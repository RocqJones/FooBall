package com.zonkesoft.fooball.di

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.zonkesoft.fooball.BuildConfig
import com.zonkesoft.fooball.core.constants.Constants
import com.zonkesoft.fooball.data_source.remote.api.FooBallApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        GsonBuilder()
            .serializeNulls() // Include null values in JSON
            .setPrettyPrinting() // Pretty print JSON for easier debugging
            .registerTypeAdapterFactory(object : com.google.gson.TypeAdapterFactory {
                override fun <T : Any?> create(gson: Gson, type: com.google.gson.reflect.TypeToken<T>): com.google.gson.TypeAdapter<T>? {
                    val delegate = gson.getDelegateAdapter(this, type)
                    return object : com.google.gson.TypeAdapter<T>() {
                        override fun write(out: com.google.gson.stream.JsonWriter, value: T) {
                            delegate.write(out, value)
                        }

                        override fun read(input: com.google.gson.stream.JsonReader): T {
                            return try {
                                delegate.read(input)
                            } catch (e: JsonSyntaxException) {
                                Log.e("GsonParsing", "JSON parsing error for type ${type.rawType.simpleName}", e)
                                Log.e("GsonParsing", "Error message: ${e.message}")
                                throw e
                            } catch (e: Exception) {
                                Log.e("GsonParsing", "Unexpected parsing error for type ${type.rawType.simpleName}", e)
                                throw e
                            }
                        }
                    }
                }
            })
            .create()
    }

    single {
        HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> {
                    HttpLoggingInterceptor.Level.BODY
                }
                else -> {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
        }
    }

    single {
        // Custom interceptor to log raw response for debugging JSON parsing issues
        okhttp3.Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            if (BuildConfig.DEBUG) {
                val responseBody = response.body
                val source = responseBody.source()
                source.request(Long.MAX_VALUE)
                val buffer = source.buffer
                val responseBodyString = buffer.clone().readUtf8()

                Log.d("NetworkResponse", "URL: ${request.url}")
                Log.d("NetworkResponse", "Response Code: ${response.code}")
                Log.d("NetworkResponse", "Raw JSON Response: $responseBodyString")

                if (responseBodyString.isNotEmpty()) {
                    try {
                        // Attempt to validate if it's valid JSON
                        com.google.gson.JsonParser.parseString(responseBodyString)
                        Log.d("NetworkResponse", "✓ Valid JSON")
                    } catch (e: Exception) {
                        Log.e("NetworkResponse", "✗ INVALID JSON: ${e.message}")
                        Log.e("NetworkResponse", "Problem: $responseBodyString")
                    }
                }
            }

            response
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<okhttp3.Interceptor>()) // Raw response logger
            .addInterceptor(get<HttpLoggingInterceptor>()) // HTTP logger
            .connectTimeout(Constants.Network.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.Network.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.Network.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single {
        get<Retrofit>().create(FooBallApiService::class.java)
    }
}
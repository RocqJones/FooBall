package com.zonkesoft.fooball.data_source.repository

import com.zonkesoft.fooball.data_source.remote.api.FooBallApiService
import com.zonkesoft.fooball.data_source.remote.mapper.toDomain
import com.zonkesoft.fooball.domain.model.Prediction
import com.zonkesoft.fooball.domain.model.PredictionsAnalysis
import com.zonkesoft.fooball.domain.model.TopPick
import com.zonkesoft.fooball.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

interface PredictionsRepository {
    fun ingestFixtures(): Flow<Result<String>>
    fun getTodayPredictions(): Flow<Result<List<Prediction>>>
    fun getAnalysis(): Flow<Result<PredictionsAnalysis>>
    fun getTopPicks(): Flow<Result<List<TopPick>>>
}

class PredictionsRepositoryImpl(
    private val apiService: FooBallApiService
) : PredictionsRepository {

    override fun ingestFixtures(): Flow<Result<String>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.ingestFixtures()
            when {
                response.isSuccessful -> {
                    val message = response.body()?.message ?: "Fixtures ingested successfully"
                    emit(Result.Success(message))
                }

                else -> {
                    emit(
                        Result.Error(
                            exception = HttpException(response),
                            message = "Failed to ingest fixtures: ${response.message()}"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(
                Result.Error(
                    exception = e,
                    message = "Network error. Please check your connection."
                )
            )
        } catch (e: Exception) {
            emit(
                Result.Error(
                    exception = e,
                    message = "An unexpected error occurred: ${e.message}"
                )
            )
        }
    }

    override fun getTodayPredictions(): Flow<Result<List<Prediction>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getTodayPredictions()
            when {
                response.isSuccessful -> {
                    val predictions = response.body()?.predictions
                        ?.map { it.toDomain() }
                        ?: emptyList()
                    emit(Result.Success(predictions))
                }

                else -> {
                    emit(
                        Result.Error(
                            exception = HttpException(response),
                            message = "Failed to fetch predictions: ${response.message()}"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(
                Result.Error(
                    exception = e,
                    message = "Network error. Please check your connection."
                )
            )
        } catch (e: Exception) {
            emit(
                Result.Error(
                    exception = e,
                    message = "An unexpected error occurred: ${e.message}"
                )
            )
        }
    }

    override fun getAnalysis(): Flow<Result<PredictionsAnalysis>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getAnalysis()
            when {
                response.isSuccessful -> {
                    val analysis = response.body()?.toDomain()
                        ?: PredictionsAnalysis(
                            0, emptyList(), emptyList(),
                            com.zonkesoft.fooball.domain.model.AnalysisSummary(
                                0, 0, 0, 0, 0.0, 0.0, 0, emptyMap()
                            )
                        )
                    emit(Result.Success(analysis))
                }

                else -> {
                    emit(
                        Result.Error(
                            exception = HttpException(response),
                            message = "Failed to fetch analysis: ${response.message()}"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(
                Result.Error(
                    exception = e,
                    message = "Network error. Please check your connection."
                )
            )
        } catch (e: Exception) {
            emit(
                Result.Error(
                    exception = e,
                    message = "An unexpected error occurred: ${e.message}"
                )
            )
        }
    }

    override fun getTopPicks(): Flow<Result<List<TopPick>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getTopPicks()
            when {
                response.isSuccessful -> {
                    val topPicks = response.body()?.topPicks
                        ?.map { it.toDomain() }
                        ?: emptyList()
                    emit(Result.Success(topPicks))
                }

                else -> {
                    emit(
                        Result.Error(
                            exception = HttpException(response),
                            message = "Failed to fetch top picks: ${response.message()}"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(
                Result.Error(
                    exception = e,
                    message = "Network error. Please check your connection."
                )
            )
        } catch (e: Exception) {
            emit(
                Result.Error(
                    exception = e,
                    message = "An unexpected error occurred: ${e.message}"
                )
            )
        }
    }
}


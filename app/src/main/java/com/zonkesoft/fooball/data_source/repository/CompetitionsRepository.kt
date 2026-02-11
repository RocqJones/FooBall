package com.zonkesoft.fooball.data_source.repository

import android.util.Log
import com.zonkesoft.fooball.data_source.remote.api.FooBallApiService
import com.zonkesoft.fooball.data_source.remote.dto.MatchesRequest
import com.zonkesoft.fooball.data_source.remote.mapper.InvalidDataException
import com.zonkesoft.fooball.data_source.remote.mapper.toDomain
import com.zonkesoft.fooball.domain.model.Competition
import com.zonkesoft.fooball.domain.model.Match
import com.zonkesoft.fooball.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

interface CompetitionsRepository {
    fun getCompetitions(): Flow<Result<List<Competition>>>
    fun getMatches(competitionCode: String): Flow<Result<List<Match>>>
}

class CompetitionsRepositoryImpl(
    private val apiService: FooBallApiService
) : CompetitionsRepository {

    override fun getCompetitions(): Flow<Result<List<Competition>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getCompetitions()
            when {
                response.isSuccessful -> {
                    try {
                        val responseBody = response.body()
                        Log.d("CompetitionsRepo", "Response body: $responseBody")
                        Log.d("CompetitionsRepo", "Competitions count: ${responseBody?.competitions?.size}")

                        val competitions = responseBody?.competitions
                            ?.mapIndexed { index, dto ->
                                try {
                                    dto.toDomain()
                                } catch (e: InvalidDataException) {
                                    Log.e("CompetitionsRepo", "Mapping error at index $index: ${e.message}")
                                    Log.e("CompetitionsRepo", "DTO that failed: $dto")
                                    throw e
                                }
                            }
                            ?: emptyList()

                        Log.d("CompetitionsRepo", "Successfully mapped ${competitions.size} competitions")
                        emit(Result.Success(competitions))
                    } catch (e: InvalidDataException) {
                        Log.e("CompetitionsRepo", "InvalidDataException during mapping", e)
                        emit(
                            Result.Error(
                                exception = e,
                                message = "Data validation error: ${e.message}"
                            )
                        )
                    }
                }

                else -> {
                    Log.e("CompetitionsRepo", "API error: ${response.code()} - ${response.message()}")
                    emit(
                        Result.Error(
                            exception = HttpException(response),
                            message = "Failed to fetch competitions: ${response.message()}"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            Log.e("CompetitionsRepo", "Network error", e)
            emit(
                Result.Error(
                    exception = e,
                    message = "Network error. Please check your connection."
                )
            )
        } catch (e: Exception) {
            Log.e("CompetitionsRepo", "Unexpected error", e)
            emit(
                Result.Error(
                    exception = e,
                    message = "An unexpected error occurred: ${e.message}"
                )
            )
        }
    }

    override fun getMatches(competitionCode: String): Flow<Result<List<Match>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getMatches(MatchesRequest(competitionCode))
            when {
                response.isSuccessful -> {
                    try {
                        val responseBody = response.body()
                        Log.d("CompetitionsRepo", "Matches response for $competitionCode: $responseBody")
                        Log.d("CompetitionsRepo", "Matches count: ${responseBody?.matches?.size}")

                        val matches = responseBody?.matches
                            ?.mapIndexed { index, dto ->
                                try {
                                    dto.toDomain()
                                } catch (e: InvalidDataException) {
                                    Log.e("CompetitionsRepo", "Match mapping error at index $index: ${e.message}")
                                    Log.e("CompetitionsRepo", "Match DTO that failed: $dto")
                                    throw e
                                }
                            }
                            ?: emptyList()

                        Log.d("CompetitionsRepo", "Successfully mapped ${matches.size} matches")
                        emit(Result.Success(matches))
                    } catch (e: InvalidDataException) {
                        Log.e("CompetitionsRepo", "InvalidDataException during match mapping", e)
                        emit(
                            Result.Error(
                                exception = e,
                                message = "Match data validation error: ${e.message}"
                            )
                        )
                    }
                }

                else -> {
                    Log.e("CompetitionsRepo", "Matches API error: ${response.code()} - ${response.message()}")
                    emit(
                        Result.Error(
                            exception = HttpException(response),
                            message = "Failed to fetch matches: ${response.message()}"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            Log.e("CompetitionsRepo", "Network error fetching matches", e)
            emit(
                Result.Error(
                    exception = e,
                    message = "Network error. Please check your connection."
                )
            )
        } catch (e: Exception) {
            Log.e("CompetitionsRepo", "Unexpected error fetching matches", e)
            emit(
                Result.Error(
                    exception = e,
                    message = "An unexpected error occurred: ${e.message}"
                )
            )
        }
    }
}


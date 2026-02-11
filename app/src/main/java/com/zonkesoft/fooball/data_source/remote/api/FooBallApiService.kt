package com.zonkesoft.fooball.data_source.remote.api

import com.zonkesoft.fooball.core.constants.Constants.ApiEndpoints
import com.zonkesoft.fooball.data_source.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FooBallApiService {

    @GET(ApiEndpoints.PREDICTIONS_TODAY)
    suspend fun getTodayPredictions(): Response<TodayPredictionsResponse>

    @GET(ApiEndpoints.TOP_PICKS)
    suspend fun getTopPicks(): Response<TopPicksResponse>

    @GET(ApiEndpoints.COMPETITIONS)
    suspend fun getCompetitions(): Response<CompetitionsResponse>

    @POST(ApiEndpoints.MATCHES)
    suspend fun getMatches(@Body request: MatchesRequest): Response<MatchesResponse>
}
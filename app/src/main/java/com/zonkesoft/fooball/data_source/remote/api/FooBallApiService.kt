package com.zonkesoft.fooball.data_source.remote.api

import com.zonkesoft.fooball.data_source.remote.dto.AnalysisResponse
import com.zonkesoft.fooball.data_source.remote.dto.FixturesIngestResponse
import com.zonkesoft.fooball.data_source.remote.dto.TodayPredictionsResponse
import com.zonkesoft.fooball.data_source.remote.dto.TopPicksResponse
import retrofit2.Response
import retrofit2.http.GET

interface FooBallApiService {

    @GET("fixtures/ingest")
    suspend fun ingestFixtures(): Response<FixturesIngestResponse>

    @GET("predictions/today")
    suspend fun getTodayPredictions(): Response<TodayPredictionsResponse>

    @GET("predictions/analysis")
    suspend fun getAnalysis(): Response<AnalysisResponse>

    @GET("predictions/top-picks")
    suspend fun getTopPicks(): Response<TopPicksResponse>
}
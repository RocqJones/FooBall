package com.zonkesoft.fooball.data_source.remote.api

import com.google.gson.Gson
import com.zonkesoft.fooball.data_source.remote.dto.AnalysisResponse
import com.zonkesoft.fooball.data_source.remote.dto.FixturesIngestResponse
import com.zonkesoft.fooball.data_source.remote.dto.TodayPredictionsResponse
import com.zonkesoft.fooball.data_source.remote.dto.TopPicksResponse
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FooBallApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: FooBallApiService
    private val gson = Gson()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(FooBallApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `ingestFixtures should return successful response`() = runTest {
        // Given
        val mockResponse = FixturesIngestResponse(
            statusCode = 200,
            status = "success",
            message = "Fixtures ingested successfully"
        )
        val jsonResponse = gson.toJson(mockResponse)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.ingestFixtures()

        // Then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals(200, response.body()?.statusCode)
        assertEquals("success", response.body()?.status)
        assertEquals("Fixtures ingested successfully", response.body()?.message)

        // Verify request
        val request = mockWebServer.takeRequest()
        assertEquals("/fixtures/ingest", request.path)
        assertEquals("GET", request.method)
    }

    @Test
    fun `ingestFixtures should handle error response`() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("{\"statusCode\":500,\"status\":\"error\",\"message\":\"Internal server error\"}")
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.ingestFixtures()

        // Then
        assertEquals(500, response.code())
        assertTrue(!response.isSuccessful)
    }

    @Test
    fun `getTodayPredictions should return predictions list`() = runTest {
        // Given
        val jsonResponse = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "Predictions retrieved successfully",
                "count": 2,
                "predictions": [
                    {
                        "fixture_id": 1234,
                        "match": "Team A vs Team B",
                        "league": "Premier League",
                        "league_logo": "https://example.com/logo.png",
                        "league_flag": "https://example.com/flag.png",
                        "home_team": "Team A",
                        "home_team_logo": "https://example.com/team_a.png",
                        "away_team": "Team B",
                        "away_team_logo": "https://example.com/team_b.png",
                        "home_win_probability": 0.65,
                        "home_win_confidence": "high",
                        "away_win_probability": 0.20,
                        "away_win_confidence": "low",
                        "draw_probability": 0.15,
                        "draw_confidence": "low",
                        "predicted_outcome": "home",
                        "predicted_outcome_probability": 0.65,
                        "goals_prediction": {
                            "bet": "Over 2.5",
                            "probability": 0.70,
                            "confidence": "high"
                        },
                        "btts_probability": 0.55,
                        "btts_confidence": "medium",
                        "value_score": 0.85,
                        "created_at": "2026-02-06T10:00:00Z"
                    }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.getTodayPredictions()

        // Then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals(200, response.body()?.statusCode)
        assertEquals("success", response.body()?.status)
        assertEquals(2, response.body()?.count)
        assertEquals(1, response.body()?.predictions?.size)

        val prediction = response.body()?.predictions?.first()
        assertEquals(1234, prediction?.fixtureId)
        assertEquals("Team A vs Team B", prediction?.match)
        assertEquals("Premier League", prediction?.league)
        assertEquals(0.65, prediction?.homeWinProbability)
        assertNotNull(prediction?.goalsPrediction)
        assertEquals("Over 2.5", prediction?.goalsPrediction?.bet)

        // Verify request
        val request = mockWebServer.takeRequest()
        assertEquals("/predictions/today", request.path)
        assertEquals("GET", request.method)
    }

    @Test
    fun `getAnalysis should return analysis data`() = runTest {
        // Given
        val jsonResponse = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "Analysis retrieved successfully",
                "total_predictions": 50,
                "best_home_wins": [
                    {
                        "match": "Team C vs Team D",
                        "league": "La Liga",
                        "league_logo": "https://example.com/laliga.png",
                        "league_flag": "https://example.com/spain.png",
                        "home_team_logo": "https://example.com/team_c.png",
                        "away_team_logo": "https://example.com/team_d.png",
                        "home_win_probability": 0.80,
                        "home_win_confidence": "very high",
                        "draw_probability": 0.15,
                        "away_win_probability": 0.05,
                        "value_score": 0.90
                    }
                ],
                "best_goals_bets": [],
                "best_btts": [],
                "best_value_bets": [],
                "summary": {
                    "total_matches": 50,
                    "high_confidence_home_wins": 15,
                    "over_2_5_count": 30,
                    "under_2_5_count": 20,
                    "avg_home_win_probability": 0.55,
                    "avg_btts_probability": 0.48,
                    "high_confidence_goals_bets": 12,
                    "matches_by_league": {
                        "Premier League": 15,
                        "La Liga": 20
                    }
                }
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.getAnalysis()

        // Then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals(200, response.body()?.statusCode)
        assertEquals("success", response.body()?.status)
        assertEquals(50, response.body()?.totalPredictions)
        assertEquals(1, response.body()?.bestHomeWins?.size)
        assertNotNull(response.body()?.summary)
        assertEquals(50, response.body()?.summary?.totalMatches)
        assertEquals(15, response.body()?.summary?.highConfidenceHomeWins)

        // Verify request
        val request = mockWebServer.takeRequest()
        assertEquals("/predictions/analysis", request.path)
        assertEquals("GET", request.method)
    }

    @Test
    fun `getTopPicks should return top picks list`() = runTest {
        // Given
        val jsonResponse = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "Top picks retrieved successfully",
                "count": 1,
                "top_picks": [
                    {
                        "fixture_id": 5678,
                        "match": "Team E vs Team F",
                        "league": "Serie A",
                        "league_logo": "https://example.com/seriea.png",
                        "league_flag": "https://example.com/italy.png",
                        "home_team_logo": "https://example.com/team_e.png",
                        "away_team_logo": "https://example.com/team_f.png",
                        "home_win_probability": 0.72,
                        "draw_probability": 0.18,
                        "away_win_probability": 0.10,
                        "goals_prediction": {
                            "bet": "Over 3.5",
                            "probability": 0.68,
                            "confidence": "high"
                        },
                        "btts_probability": 0.60,
                        "composite_score": 0.88,
                        "created_at": "2026-02-06T12:00:00Z"
                    }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.getTopPicks()

        // Then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals(200, response.body()?.statusCode)
        assertEquals("success", response.body()?.status)
        assertEquals(1, response.body()?.count)
        assertEquals(1, response.body()?.topPicks?.size)

        val topPick = response.body()?.topPicks?.first()
        assertEquals(5678, topPick?.fixtureId)
        assertEquals("Team E vs Team F", topPick?.match)
        assertEquals("Serie A", topPick?.league)
        assertEquals(0.72, topPick?.homeWinProbability)
        assertEquals(0.88, topPick?.compositeScore)
        assertNotNull(topPick?.goalsPrediction)
        assertEquals("Over 3.5", topPick?.goalsPrediction?.bet)

        // Verify request
        val request = mockWebServer.takeRequest()
        assertEquals("/predictions/top-picks", request.path)
        assertEquals("GET", request.method)
    }

    @Test
    fun `API should handle null fields gracefully`() = runTest {
        // Given - response with minimal/null fields
        val jsonResponse = """
            {
                "statusCode": null,
                "status": null,
                "message": null,
                "count": null,
                "predictions": null
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.getTodayPredictions()

        // Then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals(null, response.body()?.statusCode)
        assertEquals(null, response.body()?.status)
        assertEquals(null, response.body()?.message)
        assertEquals(null, response.body()?.predictions)
    }

    @Test
    fun `API should handle empty response body`() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("{}")
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.ingestFixtures()

        // Then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }
}


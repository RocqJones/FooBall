package com.zonkesoft.fooball.api

import com.google.gson.Gson
import com.zonkesoft.fooball.data_source.remote.api.FooBallApiService
import com.zonkesoft.fooball.data_source.remote.dto.MatchesRequest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CompetitionsApiServiceTest {

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
    fun `getCompetitions should return successful response`() = runTest {
        // Given
        val jsonResponse = """
            {
                "status": "success",
                "count": 123,
                "competitions": [
                    {
                        "code": "T1L",
                        "area": {
                            "id": 2247,
                            "name": "Turkey",
                            "code": "TUR",
                            "flag": "https://crests.football-data.org/803.svg"
                        },
                        "currentSeason": {
                            "id": 2441,
                            "startDate": "2025-08-10",
                            "endDate": "2026-05-02",
                            "currentMatchday": 24,
                            "winner": null
                        },
                        "emblem": null,
                        "name": "1. Lig",
                        "numberOfAvailableSeasons": 8,
                        "type": "LEAGUE"
                    },
                    {
                        "code": "BL2",
                        "area": {
                            "id": 2088,
                            "name": "Germany",
                            "code": "DEU",
                            "flag": "https://crests.football-data.org/759.svg"
                        },
                        "currentSeason": {
                            "id": 2415,
                            "startDate": "2025-08-01",
                            "endDate": "2026-05-17",
                            "currentMatchday": 22,
                            "winner": null
                        },
                        "emblem": "https://crests.football-data.org/BL2.png",
                        "name": "2. Bundesliga",
                        "numberOfAvailableSeasons": 30,
                        "type": "LEAGUE"
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
        val response = apiService.getCompetitions()

        // Then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals("success", response.body()?.status)
        assertEquals(123, response.body()?.count)
        assertEquals(2, response.body()?.competitions?.size)

        val firstCompetition = response.body()?.competitions?.first()
        assertEquals("T1L", firstCompetition?.code)
        assertEquals("1. Lig", firstCompetition?.name)
        assertEquals("LEAGUE", firstCompetition?.type)
        assertEquals(8, firstCompetition?.numberOfAvailableSeasons)

        // Verify request
        val request = mockWebServer.takeRequest()
        assertEquals("/competitions", request.path)
        assertEquals("GET", request.method)
    }

    @Test
    fun `getCompetitions should handle error response`() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("{\"status\":\"error\",\"message\":\"Internal server error\"}")
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.getCompetitions()

        // Then
        assertEquals(500, response.code())
        assertFalse(response.isSuccessful)
    }

    @Test
    fun `getMatches should return successful response`() = runTest {
        // Given
        val jsonResponse = """
            {
                "status": "success",
                "count": 100,
                "competition": {
                    "code": "PL",
                    "name": "Premier League"
                },
                "matches": [
                    {
                        "id": 538036,
                        "awayTeam": {
                            "id": 397,
                            "name": "Brighton & Hove Albion FC",
                            "shortName": "Brighton Hove",
                            "tla": "BHA",
                            "crest": "https://crests.football-data.org/397.png"
                        },
                        "competition": {
                            "id": 2021,
                            "name": "Premier League",
                            "code": "PL",
                            "type": "LEAGUE",
                            "emblem": "https://crests.football-data.org/PL.png"
                        },
                        "homeTeam": {
                            "id": 58,
                            "name": "Aston Villa FC",
                            "shortName": "Aston Villa",
                            "tla": "AVL",
                            "crest": "https://crests.football-data.org/58.png"
                        },
                        "matchday": 26,
                        "score": {
                            "winner": null,
                            "duration": "REGULAR",
                            "fullTime": {
                                "home": null,
                                "away": null
                            },
                            "halfTime": {
                                "home": null,
                                "away": null
                            }
                        },
                        "season": {
                            "id": 2403,
                            "startDate": "2025-08-15",
                            "endDate": "2026-05-24",
                            "currentMatchday": 26,
                            "winner": null
                        },
                        "stage": "REGULAR_SEASON",
                        "status": "TIMED",
                        "utcDate": "2026-02-11T19:30:00Z"
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
        val response = apiService.getMatches(MatchesRequest("PL"))

        // Then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertEquals("success", response.body()?.status)
        assertEquals(100, response.body()?.count)
        assertEquals("PL", response.body()?.competition?.code)
        assertEquals("Premier League", response.body()?.competition?.name)
        assertEquals(1, response.body()?.matches?.size)

        val match = response.body()?.matches?.first()
        assertEquals(538036, match?.id)
        assertEquals("Aston Villa FC", match?.homeTeam?.name)
        assertEquals("Brighton & Hove Albion FC", match?.awayTeam?.name)
        assertEquals(26, match?.matchday)
        assertEquals("REGULAR_SEASON", match?.stage)
        assertEquals("TIMED", match?.status)

        // Verify request
        val request = mockWebServer.takeRequest()
        assertEquals("/matches", request.path)
        assertEquals("POST", request.method)
        assertNotNull(request.body)
    }

    @Test
    fun `getMatches should handle error response`() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("{\"status\":\"error\",\"message\":\"Competition not found\"}")
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.getMatches(MatchesRequest("INVALID"))

        // Then
        assertEquals(404, response.code())
        assertFalse(response.isSuccessful)
    }

    @Test
    fun `getCompetitions should handle empty response body`() = runTest {
        // Given
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("{}")
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.getCompetitions()

        // Then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
    }

    @Test
    fun `getMatches should handle null fields gracefully`() = runTest {
        // Given
        val jsonResponse = """
            {
                "status": null,
                "count": null,
                "competition": null,
                "matches": null
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json")
        )

        // When
        val response = apiService.getMatches(MatchesRequest("PL"))

        // Then
        assertTrue(response.isSuccessful)
        assertNotNull(response.body())
        assertNull(response.body()?.status)
        assertNull(response.body()?.count)
        assertNull(response.body()?.competition)
        assertNull(response.body()?.matches)
    }
}


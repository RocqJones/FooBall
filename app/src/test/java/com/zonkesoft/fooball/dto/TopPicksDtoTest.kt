package com.zonkesoft.fooball.data_source.remote.dto

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class TopPicksDtoTest {

    private lateinit var gson: Gson

    @Before
    fun setup() {
        gson = Gson()
    }

    @Test
    fun `TopPicksResponse should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "Top picks retrieved",
                "count": 1,
                "top_picks": [
                    {
                        "fixture_id": 5678,
                        "match": "Team C vs Team D",
                        "league": "La Liga",
                        "league_logo": "laliga.png",
                        "league_flag": "spain.png",
                        "home_team_logo": "team_c.png",
                        "away_team_logo": "team_d.png",
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

        // When
        val response = gson.fromJson(json, TopPicksResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals(200, response.statusCode)
        assertEquals("success", response.status)
        assertEquals("Top picks retrieved", response.message)
        assertEquals(1, response.count)
        assertEquals(1, response.topPicks?.size)
    }

    @Test
    fun `TopPickDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "fixture_id": 5678,
                "match": "Team C vs Team D",
                "league": "La Liga",
                "league_logo": "laliga.png",
                "league_flag": "spain.png",
                "home_team_logo": "team_c.png",
                "away_team_logo": "team_d.png",
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
        """.trimIndent()

        // When
        val topPick = gson.fromJson(json, TopPickDto::class.java)

        // Then
        assertNotNull(topPick)
        assertEquals(5678, topPick.fixtureId)
        assertEquals("Team C vs Team D", topPick.match)
        assertEquals("La Liga", topPick.league)
        assertEquals("laliga.png", topPick.leagueLogo)
        assertEquals("spain.png", topPick.leagueFlag)
        assertEquals("team_c.png", topPick.homeTeamLogo)
        assertEquals("team_d.png", topPick.awayTeamLogo)
        assertEquals(0.72, topPick.homeWinProbability)
        assertEquals(0.18, topPick.drawProbability)
        assertEquals(0.10, topPick.awayWinProbability)
        assertEquals(0.60, topPick.bttsProbability)
        assertEquals(0.88, topPick.compositeScore)
        assertEquals("2026-02-06T12:00:00Z", topPick.createdAt)
        assertNotNull(topPick.goalsPrediction)
        assertEquals("Over 3.5", topPick.goalsPrediction?.bet)
    }

    @Test
    fun `TopPickDto should handle null fields correctly`() {
        // Given
        val json = """
            {
                "fixture_id": null,
                "match": null,
                "league": null,
                "league_logo": null,
                "league_flag": null,
                "home_team_logo": null,
                "away_team_logo": null,
                "home_win_probability": null,
                "draw_probability": null,
                "away_win_probability": null,
                "goals_prediction": null,
                "btts_probability": null,
                "composite_score": null,
                "created_at": null
            }
        """.trimIndent()

        // When
        val topPick = gson.fromJson(json, TopPickDto::class.java)

        // Then
        assertNotNull(topPick)
        assertNull(topPick.fixtureId)
        assertNull(topPick.match)
        assertNull(topPick.league)
        assertNull(topPick.goalsPrediction)
        assertNull(topPick.compositeScore)
    }

    @Test
    fun `TopPicksResponse should serialize correctly to JSON`() {
        // Given
        val response = TopPicksResponse(
            statusCode = 200,
            status = "success",
            message = "Test message",
            count = 1,
            topPicks = listOf(
                TopPickDto(
                    fixtureId = 5678,
                    match = "Team C vs Team D",
                    league = "La Liga",
                    leagueLogo = "laliga.png",
                    leagueFlag = "spain.png",
                    homeTeamLogo = "team_c.png",
                    awayTeamLogo = "team_d.png",
                    homeWinProbability = 0.72,
                    drawProbability = 0.18,
                    awayWinProbability = 0.10,
                    goalsPrediction = GoalsPredictionDto("Over 3.5", 0.68, "high"),
                    bttsProbability = 0.60,
                    compositeScore = 0.88,
                    createdAt = "2026-02-06T12:00:00Z"
                )
            )
        )

        // When
        val json = gson.toJson(response)
        val deserialized = gson.fromJson(json, TopPicksResponse::class.java)

        // Then
        assertNotNull(json)
        assertEquals(response.statusCode, deserialized.statusCode)
        assertEquals(response.status, deserialized.status)
        assertEquals(response.count, deserialized.count)
        assertEquals(response.topPicks?.size, deserialized.topPicks?.size)
    }

    @Test
    fun `TopPicksResponse with empty top_picks should deserialize correctly`() {
        // Given
        val json = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "No top picks available",
                "count": 0,
                "top_picks": []
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, TopPicksResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals(200, response.statusCode)
        assertEquals(0, response.count)
        assertEquals(0, response.topPicks?.size)
    }

    @Test
    fun `TopPickDto with missing optional fields should deserialize correctly`() {
        // Given
        val json = """
            {
                "fixture_id": 5678,
                "match": "Team C vs Team D"
            }
        """.trimIndent()

        // When
        val topPick = gson.fromJson(json, TopPickDto::class.java)

        // Then
        assertNotNull(topPick)
        assertEquals(5678, topPick.fixtureId)
        assertEquals("Team C vs Team D", topPick.match)
        assertNull(topPick.league)
        assertNull(topPick.compositeScore)
    }
}


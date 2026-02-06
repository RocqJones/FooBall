package com.zonkesoft.fooball.data_source.remote.dto

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class PredictionDtoTest {

    private lateinit var gson: Gson

    @Before
    fun setup() {
        gson = Gson()
    }

    @Test
    fun `TodayPredictionsResponse should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "Predictions retrieved",
                "count": 2,
                "predictions": [
                    {
                        "fixture_id": 1234,
                        "match": "Team A vs Team B",
                        "league": "Premier League",
                        "league_logo": "logo.png",
                        "league_flag": "flag.png",
                        "home_team": "Team A",
                        "home_team_logo": "team_a.png",
                        "away_team": "Team B",
                        "away_team_logo": "team_b.png",
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

        // When
        val response = gson.fromJson(json, TodayPredictionsResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals(200, response.statusCode)
        assertEquals("success", response.status)
        assertEquals("Predictions retrieved", response.message)
        assertEquals(2, response.count)
        assertEquals(1, response.predictions?.size)
    }

    @Test
    fun `PredictionDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "fixture_id": 1234,
                "match": "Team A vs Team B",
                "league": "Premier League",
                "league_logo": "logo.png",
                "league_flag": "flag.png",
                "home_team": "Team A",
                "home_team_logo": "team_a.png",
                "away_team": "Team B",
                "away_team_logo": "team_b.png",
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
        """.trimIndent()

        // When
        val prediction = gson.fromJson(json, PredictionDto::class.java)

        // Then
        assertNotNull(prediction)
        assertEquals(1234, prediction.fixtureId)
        assertEquals("Team A vs Team B", prediction.match)
        assertEquals("Premier League", prediction.league)
        assertEquals("logo.png", prediction.leagueLogo)
        assertEquals("flag.png", prediction.leagueFlag)
        assertEquals("Team A", prediction.homeTeam)
        assertEquals("team_a.png", prediction.homeTeamLogo)
        assertEquals("Team B", prediction.awayTeam)
        assertEquals("team_b.png", prediction.awayTeamLogo)
        assertEquals(0.65, prediction.homeWinProbability)
        assertEquals("high", prediction.homeWinConfidence)
        assertEquals(0.20, prediction.awayWinProbability)
        assertEquals("low", prediction.awayWinConfidence)
        assertEquals(0.15, prediction.drawProbability)
        assertEquals("low", prediction.drawConfidence)
        assertEquals("home", prediction.predictedOutcome)
        assertEquals(0.65, prediction.predictedOutcomeProbability)
        assertEquals(0.55, prediction.bttsProbability)
        assertEquals("medium", prediction.bttsConfidence)
        assertEquals(0.85, prediction.valueScore)
        assertEquals("2026-02-06T10:00:00Z", prediction.createdAt)
        assertNotNull(prediction.goalsPrediction)
    }

    @Test
    fun `GoalsPredictionDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "bet": "Over 2.5",
                "probability": 0.70,
                "confidence": "high"
            }
        """.trimIndent()

        // When
        val goalsPrediction = gson.fromJson(json, GoalsPredictionDto::class.java)

        // Then
        assertNotNull(goalsPrediction)
        assertEquals("Over 2.5", goalsPrediction.bet)
        assertEquals(0.70, goalsPrediction.probability)
        assertEquals("high", goalsPrediction.confidence)
    }

    @Test
    fun `PredictionDto should handle null fields correctly`() {
        // Given
        val json = """
            {
                "fixture_id": null,
                "match": null,
                "league": null,
                "league_logo": null,
                "league_flag": null,
                "home_team": null,
                "home_team_logo": null,
                "away_team": null,
                "away_team_logo": null,
                "home_win_probability": null,
                "home_win_confidence": null,
                "away_win_probability": null,
                "away_win_confidence": null,
                "draw_probability": null,
                "draw_confidence": null,
                "predicted_outcome": null,
                "predicted_outcome_probability": null,
                "goals_prediction": null,
                "btts_probability": null,
                "btts_confidence": null,
                "value_score": null,
                "created_at": null
            }
        """.trimIndent()

        // When
        val prediction = gson.fromJson(json, PredictionDto::class.java)

        // Then
        assertNotNull(prediction)
        assertNull(prediction.fixtureId)
        assertNull(prediction.match)
        assertNull(prediction.league)
        assertNull(prediction.goalsPrediction)
        assertNull(prediction.valueScore)
    }

    @Test
    fun `TodayPredictionsResponse should serialize correctly to JSON`() {
        // Given
        val response = TodayPredictionsResponse(
            statusCode = 200,
            status = "success",
            message = "Test message",
            count = 1,
            predictions = listOf(
                PredictionDto(
                    fixtureId = 1234,
                    match = "Team A vs Team B",
                    league = "Premier League",
                    leagueLogo = "logo.png",
                    leagueFlag = "flag.png",
                    homeTeam = "Team A",
                    homeTeamLogo = "team_a.png",
                    awayTeam = "Team B",
                    awayTeamLogo = "team_b.png",
                    homeWinProbability = 0.65,
                    homeWinConfidence = "high",
                    awayWinProbability = 0.20,
                    awayWinConfidence = "low",
                    drawProbability = 0.15,
                    drawConfidence = "low",
                    predictedOutcome = "home",
                    predictedOutcomeProbability = 0.65,
                    goalsPrediction = GoalsPredictionDto("Over 2.5", 0.70, "high"),
                    bttsProbability = 0.55,
                    bttsConfidence = "medium",
                    valueScore = 0.85,
                    createdAt = "2026-02-06T10:00:00Z"
                )
            )
        )

        // When
        val json = gson.toJson(response)
        val deserialized = gson.fromJson(json, TodayPredictionsResponse::class.java)

        // Then
        assertNotNull(json)
        assertEquals(response.statusCode, deserialized.statusCode)
        assertEquals(response.status, deserialized.status)
        assertEquals(response.count, deserialized.count)
        assertEquals(response.predictions?.size, deserialized.predictions?.size)
    }

    @Test
    fun `PredictionDto with missing optional fields should deserialize correctly`() {
        // Given - JSON with only required fields
        val json = """
            {
                "fixture_id": 1234,
                "match": "Team A vs Team B"
            }
        """.trimIndent()

        // When
        val prediction = gson.fromJson(json, PredictionDto::class.java)

        // Then
        assertNotNull(prediction)
        assertEquals(1234, prediction.fixtureId)
        assertEquals("Team A vs Team B", prediction.match)
        assertNull(prediction.league)
        assertNull(prediction.homeTeam)
        assertNull(prediction.valueScore)
    }
}


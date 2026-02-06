package com.zonkesoft.fooball.data_source.remote.dto

import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AnalysisDtoTest {

    private lateinit var gson: Gson

    @Before
    fun setup() {
        gson = Gson()
    }

    @Test
    fun `AnalysisResponse should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "statusCode": 200,
                "status": "success",
                "message": "Analysis retrieved",
                "total_predictions": 50,
                "best_home_wins": [
                    {
                        "match": "Team A vs Team B",
                        "league": "Premier League",
                        "league_logo": "logo.png",
                        "league_flag": "flag.png",
                        "home_team_logo": "team_a.png",
                        "away_team_logo": "team_b.png",
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

        // When
        val response = gson.fromJson(json, AnalysisResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals(200, response.statusCode)
        assertEquals("success", response.status)
        assertEquals("Analysis retrieved", response.message)
        assertEquals(50, response.totalPredictions)
        assertEquals(1, response.bestHomeWins?.size)
        assertEquals(0, response.bestGoalsBets?.size)
        assertNotNull(response.summary)
    }

    @Test
    fun `BestHomeWinDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "match": "Team A vs Team B",
                "league": "Premier League",
                "league_logo": "logo.png",
                "league_flag": "flag.png",
                "home_team_logo": "team_a.png",
                "away_team_logo": "team_b.png",
                "home_win_probability": 0.80,
                "home_win_confidence": "very high",
                "draw_probability": 0.15,
                "away_win_probability": 0.05,
                "value_score": 0.90
            }
        """.trimIndent()

        // When
        val bestHomeWin = gson.fromJson(json, BestHomeWinDto::class.java)

        // Then
        assertNotNull(bestHomeWin)
        assertEquals("Team A vs Team B", bestHomeWin.match)
        assertEquals("Premier League", bestHomeWin.league)
        assertEquals("logo.png", bestHomeWin.leagueLogo)
        assertEquals("flag.png", bestHomeWin.leagueFlag)
        assertEquals("team_a.png", bestHomeWin.homeTeamLogo)
        assertEquals("team_b.png", bestHomeWin.awayTeamLogo)
        assertEquals(0.80, bestHomeWin.homeWinProbability)
        assertEquals("very high", bestHomeWin.homeWinConfidence)
        assertEquals(0.15, bestHomeWin.drawProbability)
        assertEquals(0.05, bestHomeWin.awayWinProbability)
        assertEquals(0.90, bestHomeWin.valueScore)
    }

    @Test
    fun `BestGoalsBetDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "match": "Team C vs Team D",
                "league_logo": "logo.png",
                "league_flag": "flag.png",
                "home_team_logo": "team_c.png",
                "away_team_logo": "team_d.png",
                "goals_prediction": {
                    "bet": "Over 2.5",
                    "probability": 0.75,
                    "confidence": "high"
                }
            }
        """.trimIndent()

        // When
        val bestGoalsBet = gson.fromJson(json, BestGoalsBetDto::class.java)

        // Then
        assertNotNull(bestGoalsBet)
        assertEquals("Team C vs Team D", bestGoalsBet.match)
        assertEquals("logo.png", bestGoalsBet.leagueLogo)
        assertEquals("flag.png", bestGoalsBet.leagueFlag)
        assertEquals("team_c.png", bestGoalsBet.homeTeamLogo)
        assertEquals("team_d.png", bestGoalsBet.awayTeamLogo)
        assertNotNull(bestGoalsBet.goalsPrediction)
        assertEquals("Over 2.5", bestGoalsBet.goalsPrediction?.bet)
        assertEquals(0.75, bestGoalsBet.goalsPrediction?.probability)
    }

    @Test
    fun `BestBttsDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "match": "Team E vs Team F",
                "league_logo": "logo.png",
                "league_flag": "flag.png",
                "home_team_logo": "team_e.png",
                "away_team_logo": "team_f.png",
                "btts_probability": 0.72,
                "btts_confidence": "high"
            }
        """.trimIndent()

        // When
        val bestBtts = gson.fromJson(json, BestBttsDto::class.java)

        // Then
        assertNotNull(bestBtts)
        assertEquals("Team E vs Team F", bestBtts.match)
        assertEquals("logo.png", bestBtts.leagueLogo)
        assertEquals("flag.png", bestBtts.leagueFlag)
        assertEquals("team_e.png", bestBtts.homeTeamLogo)
        assertEquals("team_f.png", bestBtts.awayTeamLogo)
        assertEquals(0.72, bestBtts.bttsProbability)
        assertEquals("high", bestBtts.bttsConfidence)
    }

    @Test
    fun `BestValueBetDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "match": "Team G vs Team H",
                "league_logo": "logo.png",
                "league_flag": "flag.png",
                "home_team_logo": "team_g.png",
                "away_team_logo": "team_h.png",
                "value_score": 0.95,
                "predicted_outcome": "home",
                "probability": 0.78
            }
        """.trimIndent()

        // When
        val bestValueBet = gson.fromJson(json, BestValueBetDto::class.java)

        // Then
        assertNotNull(bestValueBet)
        assertEquals("Team G vs Team H", bestValueBet.match)
        assertEquals("logo.png", bestValueBet.leagueLogo)
        assertEquals("flag.png", bestValueBet.leagueFlag)
        assertEquals("team_g.png", bestValueBet.homeTeamLogo)
        assertEquals("team_h.png", bestValueBet.awayTeamLogo)
        assertEquals(0.95, bestValueBet.valueScore)
        assertEquals("home", bestValueBet.predictedOutcome)
        assertEquals(0.78, bestValueBet.probability)
    }

    @Test
    fun `SummaryDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "total_matches": 50,
                "high_confidence_home_wins": 15,
                "over_2_5_count": 30,
                "under_2_5_count": 20,
                "avg_home_win_probability": 0.55,
                "avg_btts_probability": 0.48,
                "high_confidence_goals_bets": 12,
                "matches_by_league": {
                    "Premier League": 15,
                    "La Liga": 20,
                    "Serie A": 15
                }
            }
        """.trimIndent()

        // When
        val summary = gson.fromJson(json, SummaryDto::class.java)

        // Then
        assertNotNull(summary)
        assertEquals(50, summary.totalMatches)
        assertEquals(15, summary.highConfidenceHomeWins)
        assertEquals(30, summary.over25Count)
        assertEquals(20, summary.under25Count)
        assertEquals(0.55, summary.avgHomeWinProbability)
        assertEquals(0.48, summary.avgBttsProbability)
        assertEquals(12, summary.highConfidenceGoalsBets)
        assertNotNull(summary.matchesByLeague)
        assertEquals(3, summary.matchesByLeague?.size)
        assertEquals(15, summary.matchesByLeague?.get("Premier League"))
        assertEquals(20, summary.matchesByLeague?.get("La Liga"))
    }

    @Test
    fun `AnalysisResponse should handle null fields correctly`() {
        // Given
        val json = """
            {
                "statusCode": null,
                "status": null,
                "message": null,
                "total_predictions": null,
                "best_home_wins": null,
                "best_goals_bets": null,
                "best_btts": null,
                "best_value_bets": null,
                "summary": null
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, AnalysisResponse::class.java)

        // Then
        assertNotNull(response)
        assertNull(response.statusCode)
        assertNull(response.status)
        assertNull(response.totalPredictions)
        assertNull(response.bestHomeWins)
        assertNull(response.summary)
    }

    @Test
    fun `AnalysisResponse should serialize correctly to JSON`() {
        // Given
        val response = AnalysisResponse(
            statusCode = 200,
            status = "success",
            message = "Test analysis",
            totalPredictions = 50,
            bestHomeWins = listOf(
                BestHomeWinDto(
                    match = "Team A vs Team B",
                    league = "Premier League",
                    leagueLogo = "logo.png",
                    leagueFlag = "flag.png",
                    homeTeamLogo = "team_a.png",
                    awayTeamLogo = "team_b.png",
                    homeWinProbability = 0.80,
                    homeWinConfidence = "very high",
                    drawProbability = 0.15,
                    awayWinProbability = 0.05,
                    valueScore = 0.90
                )
            ),
            bestGoalsBets = emptyList(),
            bestBtts = emptyList(),
            bestValueBets = emptyList(),
            summary = SummaryDto(
                totalMatches = 50,
                highConfidenceHomeWins = 15,
                over25Count = 30,
                under25Count = 20,
                avgHomeWinProbability = 0.55,
                avgBttsProbability = 0.48,
                highConfidenceGoalsBets = 12,
                matchesByLeague = mapOf("Premier League" to 15)
            )
        )

        // When
        val json = gson.toJson(response)
        val deserialized = gson.fromJson(json, AnalysisResponse::class.java)

        // Then
        assertNotNull(json)
        assertEquals(response.statusCode, deserialized.statusCode)
        assertEquals(response.status, deserialized.status)
        assertEquals(response.totalPredictions, deserialized.totalPredictions)
        assertEquals(response.bestHomeWins?.size, deserialized.bestHomeWins?.size)
    }

    @Test
    fun `SummaryDto should handle empty matches_by_league map`() {
        // Given
        val json = """
            {
                "total_matches": 0,
                "high_confidence_home_wins": 0,
                "over_2_5_count": 0,
                "under_2_5_count": 0,
                "avg_home_win_probability": 0.0,
                "avg_btts_probability": 0.0,
                "high_confidence_goals_bets": 0,
                "matches_by_league": {}
            }
        """.trimIndent()

        // When
        val summary = gson.fromJson(json, SummaryDto::class.java)

        // Then
        assertNotNull(summary)
        assertEquals(0, summary.totalMatches)
        assertNotNull(summary.matchesByLeague)
        assertTrue(summary.matchesByLeague?.isEmpty() == true)
    }
}


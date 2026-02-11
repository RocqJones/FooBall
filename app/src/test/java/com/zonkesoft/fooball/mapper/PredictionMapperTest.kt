package com.zonkesoft.fooball.mapper

import com.zonkesoft.fooball.core.extensions.orDefault
import com.zonkesoft.fooball.data_source.remote.dto.*
import com.zonkesoft.fooball.data_source.remote.mapper.toDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class PredictionMapperTest {

    @Test
    fun `GoalsPredictionDto toDomain should succeed with all fields`() {
        val dto = GoalsPredictionDto("Over 2.5", 0.75, "high")
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("Over 2.5", result.bet)
        assertEquals(0.75, result.probability.orDefault(), 0.0001)
        assertEquals("high", result.confidence)
    }

    @Test
    fun `GoalsPredictionDto toDomain should use defaults when fields are null`() {
        val dto = GoalsPredictionDto(null, null, null)
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("", result.bet)
        assertEquals(0.0, result.probability.orDefault(), 0.0001)
        assertEquals("", result.confidence)
    }

    @Test
    fun `PredictionDto toDomain should succeed with all fields`() {
        val dto = PredictionDto(
            1234,
            "Team A vs Team B",
            "Premier League",
            "logo.png",
            "flag.png",
            "Team A",
            "team_a.png",
            "Team B",
            "team_b.png",
            0.65,
            "high",
            0.20,
            "low",
            0.15,
            "low",
            "Home Win",
            0.65,
            GoalsPredictionDto("Over 2.5", 0.70, "medium"),
            0.55,
            "medium",
            0.85,
            "2026-02-06T12:00:00Z"
        )
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(1234, result.fixtureId)
        assertEquals("Team A vs Team B", result.match)
        assertEquals("Team A", result.homeTeam)
        assertEquals("Team B", result.awayTeam)
    }

    @Test
    fun `PredictionDto toDomain should use defaults when fields are null`() {
        val dto = PredictionDto(
            null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null,
            null, null, null, null
        )
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(0, result.fixtureId)
        assertEquals("", result.match)
        assertEquals("", result.homeTeam)
        assertEquals("", result.awayTeam)
        assertEquals(0.0, result.homeWinProbability.orDefault(), 0.0001)
    }

    @Test
    fun `TopPickDto toDomain should succeed with all fields`() {
        val dto = TopPickDto(
            5678,
            "Team C vs Team D",
            "Serie A",
            "logo.png",
            "flag.png",
            "team_c.png",
            "team_d.png",
            0.72,
            0.18,
            0.10,
            GoalsPredictionDto("Over 3.5", 0.68, "high"),
            0.60,
            0.88,
            "2026-02-06T12:00:00Z"
        )
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(5678, result.fixtureId)
        assertEquals("Team C vs Team D", result.match)
        assertEquals(0.72, result.homeWinProbability.orDefault(), 0.0001)
        assertEquals(0.88, result.compositeScore.orDefault(), 0.0001)
    }

    @Test
    fun `TopPickDto toDomain should use defaults when fields are null`() {
        val dto = TopPickDto(
            null, null, null, null, null, null, null,
            null, null, null, null, null, null, null
        )
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(0, result.fixtureId)
        assertEquals("", result.match)
        assertEquals(0.0, result.homeWinProbability.orDefault(), 0.0001)
        assertEquals(0.0, result.compositeScore.orDefault(), 0.0001)
    }
}


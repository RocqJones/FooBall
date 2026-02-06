package com.zonkesoft.fooball.mapper

import com.zonkesoft.fooball.data_source.remote.dto.BestBttsDto
import com.zonkesoft.fooball.data_source.remote.dto.BestHomeWinDto
import com.zonkesoft.fooball.data_source.remote.dto.GoalsPredictionDto
import com.zonkesoft.fooball.data_source.remote.dto.PredictionDto
import com.zonkesoft.fooball.data_source.remote.dto.TopPickDto
import com.zonkesoft.fooball.data_source.remote.mapper.InvalidDataException
import com.zonkesoft.fooball.data_source.remote.mapper.toDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Tests for the PredictionMapper functions, specifically focusing on
 * InvalidDataException handling for required fields.
 */
class PredictionMapperTest {

    // ============ GoalsPredictionDto Tests ============

    @Test
    fun `GoalsPredictionDto toDomain should succeed with all fields`() {
        // Given
        val dto = GoalsPredictionDto(
            bet = "Over 2.5",
            probability = 0.75,
            confidence = "high"
        )

        // When
        val result = dto.toDomain()

        // Then
        assertNotNull(result)
        assertEquals("Over 2.5", result.bet)
        assertEquals(0.75, result.probability, 0.0001)
        assertEquals("high", result.confidence)
    }

    @Test(expected = InvalidDataException::class)
    fun `GoalsPredictionDto toDomain should throw when bet is null`() {
        // Given
        val dto = GoalsPredictionDto(
            bet = null,
            probability = 0.75,
            confidence = "high"
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `GoalsPredictionDto toDomain should throw when probability is null`() {
        // Given
        val dto = GoalsPredictionDto(
            bet = "Over 2.5",
            probability = null,
            confidence = "high"
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `GoalsPredictionDto toDomain should throw when confidence is null`() {
        // Given
        val dto = GoalsPredictionDto(
            bet = "Over 2.5",
            probability = 0.75,
            confidence = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    // ============ PredictionDto Tests ============

    @Test
    fun `PredictionDto toDomain should succeed with required fields`() {
        // Given
        val dto = PredictionDto(
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
            goalsPrediction = null,
            bttsProbability = 0.55,
            bttsConfidence = "medium",
            valueScore = 0.85,
            createdAt = "2026-02-06T10:00:00Z"
        )

        // When
        val result = dto.toDomain()

        // Then
        assertNotNull(result)
        assertEquals(1234, result.fixtureId)
        assertEquals("Team A vs Team B", result.match)
        assertEquals("Team A", result.homeTeam)
        assertEquals("Team B", result.awayTeam)
    }

    @Test(expected = InvalidDataException::class)
    fun `PredictionDto toDomain should throw when fixtureId is null`() {
        // Given
        val dto = PredictionDto(
            fixtureId = null,
            match = "Team A vs Team B",
            league = null,
            leagueLogo = null,
            leagueFlag = null,
            homeTeam = "Team A",
            homeTeamLogo = null,
            awayTeam = "Team B",
            awayTeamLogo = null,
            homeWinProbability = null,
            homeWinConfidence = null,
            awayWinProbability = null,
            awayWinConfidence = null,
            drawProbability = null,
            drawConfidence = null,
            predictedOutcome = null,
            predictedOutcomeProbability = null,
            goalsPrediction = null,
            bttsProbability = null,
            bttsConfidence = null,
            valueScore = null,
            createdAt = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `PredictionDto toDomain should throw when match is null`() {
        // Given
        val dto = PredictionDto(
            fixtureId = 1234,
            match = null,
            league = null,
            leagueLogo = null,
            leagueFlag = null,
            homeTeam = "Team A",
            homeTeamLogo = null,
            awayTeam = "Team B",
            awayTeamLogo = null,
            homeWinProbability = null,
            homeWinConfidence = null,
            awayWinProbability = null,
            awayWinConfidence = null,
            drawProbability = null,
            drawConfidence = null,
            predictedOutcome = null,
            predictedOutcomeProbability = null,
            goalsPrediction = null,
            bttsProbability = null,
            bttsConfidence = null,
            valueScore = null,
            createdAt = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `PredictionDto toDomain should throw when homeTeam is null`() {
        // Given
        val dto = PredictionDto(
            fixtureId = 1234,
            match = "Team A vs Team B",
            league = null,
            leagueLogo = null,
            leagueFlag = null,
            homeTeam = null,
            homeTeamLogo = null,
            awayTeam = "Team B",
            awayTeamLogo = null,
            homeWinProbability = null,
            homeWinConfidence = null,
            awayWinProbability = null,
            awayWinConfidence = null,
            drawProbability = null,
            drawConfidence = null,
            predictedOutcome = null,
            predictedOutcomeProbability = null,
            goalsPrediction = null,
            bttsProbability = null,
            bttsConfidence = null,
            valueScore = null,
            createdAt = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `PredictionDto toDomain should throw when awayTeam is null`() {
        // Given
        val dto = PredictionDto(
            fixtureId = 1234,
            match = "Team A vs Team B",
            league = null,
            leagueLogo = null,
            leagueFlag = null,
            homeTeam = "Team A",
            homeTeamLogo = null,
            awayTeam = null,
            awayTeamLogo = null,
            homeWinProbability = null,
            homeWinConfidence = null,
            awayWinProbability = null,
            awayWinConfidence = null,
            drawProbability = null,
            drawConfidence = null,
            predictedOutcome = null,
            predictedOutcomeProbability = null,
            goalsPrediction = null,
            bttsProbability = null,
            bttsConfidence = null,
            valueScore = null,
            createdAt = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test
    fun `PredictionDto toDomain should use empty string for nullable non-required fields`() {
        // Given
        val dto = PredictionDto(
            fixtureId = 1234,
            match = "Team A vs Team B",
            league = null,
            leagueLogo = null,
            leagueFlag = null,
            homeTeam = "Team A",
            homeTeamLogo = null,
            awayTeam = "Team B",
            awayTeamLogo = null,
            homeWinProbability = null,
            homeWinConfidence = null,
            awayWinProbability = null,
            awayWinConfidence = null,
            drawProbability = null,
            drawConfidence = null,
            predictedOutcome = null,
            predictedOutcomeProbability = null,
            goalsPrediction = null,
            bttsProbability = null,
            bttsConfidence = null,
            valueScore = null,
            createdAt = null
        )

        // When
        val result = dto.toDomain()

        // Then - Uses defaults for optional fields
        assertEquals("", result.league)
        assertEquals("", result.leagueLogo)
        assertEquals("", result.homeTeamLogo)
        assertEquals("", result.awayTeamLogo)
        assertEquals(0.0, result.homeWinProbability, 0.0001)
    }

    // ============ TopPickDto Tests ============

    @Test(expected = InvalidDataException::class)
    fun `TopPickDto toDomain should throw when fixtureId is null`() {
        // Given
        val dto = TopPickDto(
            fixtureId = null,
            match = "Team C vs Team D",
            league = null,
            leagueLogo = null,
            leagueFlag = null,
            homeTeamLogo = null,
            awayTeamLogo = null,
            homeWinProbability = null,
            drawProbability = null,
            awayWinProbability = null,
            goalsPrediction = null,
            bttsProbability = null,
            compositeScore = null,
            createdAt = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `TopPickDto toDomain should throw when match is null`() {
        // Given
        val dto = TopPickDto(
            fixtureId = 5678,
            match = null,
            league = null,
            leagueLogo = null,
            leagueFlag = null,
            homeTeamLogo = null,
            awayTeamLogo = null,
            homeWinProbability = null,
            drawProbability = null,
            awayWinProbability = null,
            goalsPrediction = null,
            bttsProbability = null,
            compositeScore = null,
            createdAt = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    // ============ BestHomeWinDto Tests ============

    @Test(expected = InvalidDataException::class)
    fun `BestHomeWinDto toDomain should throw when match is null`() {
        // Given
        val dto = BestHomeWinDto(
            match = null,
            league = null,
            leagueLogo = null,
            leagueFlag = null,
            homeTeamLogo = null,
            awayTeamLogo = null,
            homeWinProbability = null,
            homeWinConfidence = null,
            drawProbability = null,
            awayWinProbability = null,
            valueScore = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    // ============ BestBttsDto Tests ============

    @Test(expected = InvalidDataException::class)
    fun `BestBttsDto toDomain should throw when match is null`() {
        // Given
        val dto = BestBttsDto(
            match = null,
            leagueLogo = null,
            leagueFlag = null,
            homeTeamLogo = null,
            awayTeamLogo = null,
            bttsProbability = null,
            bttsConfidence = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    // ============ Exception Message Tests ============

    @Test
    fun `InvalidDataException should have descriptive message for fixtureId`() {
        // Given
        val dto = PredictionDto(
            fixtureId = null,
            match = "Test",
            league = null,
            leagueLogo = null,
            leagueFlag = null,
            homeTeam = "A",
            homeTeamLogo = null,
            awayTeam = "B",
            awayTeamLogo = null,
            homeWinProbability = null,
            homeWinConfidence = null,
            awayWinProbability = null,
            awayWinConfidence = null,
            drawProbability = null,
            drawConfidence = null,
            predictedOutcome = null,
            predictedOutcomeProbability = null,
            goalsPrediction = null,
            bttsProbability = null,
            bttsConfidence = null,
            valueScore = null,
            createdAt = null
        )

        // When & Then
        try {
            dto.toDomain()
        } catch (e: InvalidDataException) {
            assertEquals("PredictionDto.fixtureId cannot be null", e.message)
        }
    }

    @Test
    fun `InvalidDataException should be IllegalArgumentException subclass`() {
        // Given
        val exception = InvalidDataException("test")

        // Then
        assert(exception is IllegalArgumentException)
    }
}


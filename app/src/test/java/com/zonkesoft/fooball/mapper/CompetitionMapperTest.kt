package com.zonkesoft.fooball.mapper

import com.zonkesoft.fooball.data_source.remote.dto.*
import com.zonkesoft.fooball.data_source.remote.mapper.InvalidDataException
import com.zonkesoft.fooball.data_source.remote.mapper.toDomain
import org.junit.Assert.*
import org.junit.Test

/**
 * Tests for the CompetitionMapper functions, specifically focusing on
 * InvalidDataException handling for required fields.
 */
class CompetitionMapperTest {

    // ============ AreaDto Tests ============

    @Test
    fun `AreaDto toDomain should succeed with all fields`() {
        // Given
        val dto = AreaDto(
            id = 2088,
            name = "England",
            code = "ENG",
            flag = "https://crests.football-data.org/770.svg"
        )

        // When
        val result = dto.toDomain()

        // Then
        assertNotNull(result)
        assertEquals(2088, result.id)
        assertEquals("England", result.name)
        assertEquals("ENG", result.code)
        assertEquals("https://crests.football-data.org/770.svg", result.flag)
    }

    @Test(expected = InvalidDataException::class)
    fun `AreaDto toDomain should throw when id is null`() {
        // Given
        val dto = AreaDto(
            id = null,
            name = "England",
            code = "ENG",
            flag = "https://crests.football-data.org/770.svg"
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `AreaDto toDomain should throw when name is null`() {
        // Given
        val dto = AreaDto(
            id = 2088,
            name = null,
            code = "ENG",
            flag = "https://crests.football-data.org/770.svg"
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `AreaDto toDomain should throw when code is null`() {
        // Given
        val dto = AreaDto(
            id = 2088,
            name = "England",
            code = null,
            flag = "https://crests.football-data.org/770.svg"
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test
    fun `AreaDto toDomain should handle null flag with empty string`() {
        // Given
        val dto = AreaDto(
            id = 2088,
            name = "England",
            code = "ENG",
            flag = null
        )

        // When
        val result = dto.toDomain()

        // Then
        assertNotNull(result)
        assertEquals("", result.flag)
    }

    // ============ SeasonDto Tests ============

    @Test
    fun `SeasonDto toDomain should succeed with all fields`() {
        // Given
        val dto = SeasonDto(
            id = 2403,
            startDate = "2025-08-15",
            endDate = "2026-05-24",
            currentMatchday = 26,
            winner = null
        )

        // When
        val result = dto.toDomain()

        // Then
        assertNotNull(result)
        assertEquals(2403, result.id)
        assertEquals("2025-08-15", result.startDate)
        assertEquals("2026-05-24", result.endDate)
        assertEquals(26, result.currentMatchday)
        assertNull(result.winner)
    }

    @Test(expected = InvalidDataException::class)
    fun `SeasonDto toDomain should throw when id is null`() {
        // Given
        val dto = SeasonDto(
            id = null,
            startDate = "2025-08-15",
            endDate = "2026-05-24",
            currentMatchday = 26,
            winner = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test
    fun `SeasonDto toDomain should allow null currentMatchday`() {
        // Given - currentMatchday can be null for completed/not started competitions
        val dto = SeasonDto(
            id = 2403,
            startDate = "2025-08-15",
            endDate = "2026-05-24",
            currentMatchday = null,
            winner = null
        )

        // When
        val result = dto.toDomain()

        // Then - should succeed with null matchday
        assertNotNull(result)
        assertNull(result.currentMatchday)
    }

    // ============ CompetitionDto Tests ============

    @Test
    fun `CompetitionDto toDomain should succeed with all fields`() {
        // Given
        val dto = CompetitionDto(
            code = "PL",
            area = AreaDto(
                id = 2088,
                name = "England",
                code = "ENG",
                flag = "https://crests.football-data.org/770.svg"
            ),
            currentSeason = SeasonDto(
                id = 2403,
                startDate = "2025-08-15",
                endDate = "2026-05-24",
                currentMatchday = 26,
                winner = null
            ),
            emblem = "https://crests.football-data.org/PL.png",
            name = "Premier League",
            numberOfAvailableSeasons = 30,
            type = "LEAGUE"
        )

        // When
        val result = dto.toDomain()

        // Then
        assertNotNull(result)
        assertEquals("PL", result.code)
        assertEquals("Premier League", result.name)
        assertEquals("LEAGUE", result.type)
        assertEquals(30, result.numberOfAvailableSeasons)
        assertEquals("https://crests.football-data.org/PL.png", result.emblem)
    }

    @Test(expected = InvalidDataException::class)
    fun `CompetitionDto toDomain should throw when code is null`() {
        // Given
        val dto = CompetitionDto(
            code = null,
            area = AreaDto(2088, "England", "ENG", null),
            currentSeason = SeasonDto(2403, "2025-08-15", "2026-05-24", 26, null),
            emblem = null,
            name = "Premier League",
            numberOfAvailableSeasons = 30,
            type = "LEAGUE"
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `CompetitionDto toDomain should throw when area is null`() {
        // Given
        val dto = CompetitionDto(
            code = "PL",
            area = null,
            currentSeason = SeasonDto(2403, "2025-08-15", "2026-05-24", 26, null),
            emblem = null,
            name = "Premier League",
            numberOfAvailableSeasons = 30,
            type = "LEAGUE"
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    // ============ TeamDto Tests ============

    @Test
    fun `TeamDto toDomain should succeed with all fields`() {
        // Given
        val dto = TeamDto(
            id = 58,
            name = "Aston Villa FC",
            shortName = "Aston Villa",
            tla = "AVL",
            crest = "https://crests.football-data.org/58.png"
        )

        // When
        val result = dto.toDomain()

        // Then
        assertNotNull(result)
        assertEquals(58, result.id)
        assertEquals("Aston Villa FC", result.name)
        assertEquals("Aston Villa", result.shortName)
        assertEquals("AVL", result.tla)
        assertEquals("https://crests.football-data.org/58.png", result.crest)
    }

    @Test(expected = InvalidDataException::class)
    fun `TeamDto toDomain should throw when id is null`() {
        // Given
        val dto = TeamDto(
            id = null,
            name = "Aston Villa FC",
            shortName = "Aston Villa",
            tla = "AVL",
            crest = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `TeamDto toDomain should throw when name is null`() {
        // Given
        val dto = TeamDto(
            id = 58,
            name = null,
            shortName = "Aston Villa",
            tla = "AVL",
            crest = null
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    // ============ ScoreDto Tests ============

    @Test
    fun `ScoreDto toDomain should succeed with all fields`() {
        // Given
        val dto = ScoreDto(
            winner = "HOME_TEAM",
            duration = "REGULAR",
            fullTime = ScoreDetailDto(home = 2, away = 1),
            halfTime = ScoreDetailDto(home = 1, away = 0)
        )

        // When
        val result = dto.toDomain()

        // Then
        assertNotNull(result)
        assertEquals("HOME_TEAM", result.winner)
        assertEquals("REGULAR", result.duration)
        assertEquals(2, result.fullTime?.home)
        assertEquals(1, result.fullTime?.away)
    }

    @Test
    fun `ScoreDto toDomain should use defaults when duration is null`() {
        // Given - duration can be null, uses "REGULAR" as default
        val dto = ScoreDto(
            winner = null,
            duration = null,
            fullTime = ScoreDetailDto(home = null, away = null),
            halfTime = ScoreDetailDto(home = null, away = null)
        )

        // When
        val result = dto.toDomain()

        // Then - should use default values
        assertNotNull(result)
        assertEquals("REGULAR", result.duration) // Default value
        assertNull(result.winner)
        assertNull(result.fullTime?.home)
        assertNull(result.fullTime?.away)
    }

    @Test
    fun `ScoreDetailDto toDomain should handle null scores`() {
        // Given
        val dto = ScoreDetailDto(home = null, away = null)

        // When
        val result = dto.toDomain()

        // Then
        assertNotNull(result)
        assertNull(result.home)
        assertNull(result.away)
    }

    // ============ MatchDto Tests ============

    @Test
    fun `MatchDto toDomain should succeed with all fields`() {
        // Given
        val dto = MatchDto(
            id = 538036,
            awayTeam = TeamDto(397, "Brighton & Hove Albion FC", "Brighton Hove", "BHA", null),
            competition = MatchCompetitionDto(2021, "Premier League", "PL", "LEAGUE", null),
            homeTeam = TeamDto(58, "Aston Villa FC", "Aston Villa", "AVL", null),
            matchday = 26,
            score = ScoreDto(null, "REGULAR", ScoreDetailDto(null, null), ScoreDetailDto(null, null)),
            season = SeasonDto(2403, "2025-08-15", "2026-05-24", 26, null),
            stage = "REGULAR_SEASON",
            status = "TIMED",
            utcDate = "2026-02-11T19:30:00Z"
        )

        // When
        val result = dto.toDomain()

        // Then
        assertNotNull(result)
        assertEquals(538036, result.id)
        assertEquals("Aston Villa FC", result.homeTeam?.name)
        assertEquals("Brighton & Hove Albion FC", result.awayTeam?.name)
        assertEquals(26, result.matchday)
        assertEquals("REGULAR_SEASON", result.stage)
        assertEquals("TIMED", result.status)
    }

    @Test(expected = InvalidDataException::class)
    fun `MatchDto toDomain should throw when id is null`() {
        // Given
        val dto = MatchDto(
            id = null,
            awayTeam = TeamDto(397, "Brighton & Hove Albion FC", "Brighton Hove", "BHA", null),
            competition = MatchCompetitionDto(2021, "Premier League", "PL", "LEAGUE", null),
            homeTeam = TeamDto(58, "Aston Villa FC", "Aston Villa", "AVL", null),
            matchday = 26,
            score = ScoreDto(null, "REGULAR", ScoreDetailDto(null, null), ScoreDetailDto(null, null)),
            season = SeasonDto(2403, "2025-08-15", "2026-05-24", 26, null),
            stage = "REGULAR_SEASON",
            status = "TIMED",
            utcDate = "2026-02-11T19:30:00Z"
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test(expected = InvalidDataException::class)
    fun `MatchDto toDomain should throw when homeTeam is null`() {
        // Given
        val dto = MatchDto(
            id = 538036,
            awayTeam = TeamDto(397, "Brighton & Hove Albion FC", "Brighton Hove", "BHA", null),
            competition = MatchCompetitionDto(2021, "Premier League", "PL", "LEAGUE", null),
            homeTeam = null,
            matchday = 26,
            score = ScoreDto(null, "REGULAR", ScoreDetailDto(null, null), ScoreDetailDto(null, null)),
            season = SeasonDto(2403, "2025-08-15", "2026-05-24", 26, null),
            stage = "REGULAR_SEASON",
            status = "TIMED",
            utcDate = "2026-02-11T19:30:00Z"
        )

        // When
        dto.toDomain()

        // Then - exception thrown
    }

    @Test
    fun `InvalidDataException messages should be descriptive`() {
        // Given
        val dto = AreaDto(
            id = null,
            name = "England",
            code = "ENG",
            flag = null
        )

        // When & Then
        try {
            dto.toDomain()
            fail("Expected InvalidDataException")
        } catch (e: InvalidDataException) {
            assertEquals("AreaDto.id cannot be null", e.message)
        }
    }
}


package com.zonkesoft.fooball.mapper

import com.zonkesoft.fooball.data_source.remote.dto.*
import com.zonkesoft.fooball.data_source.remote.mapper.toDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class CompetitionMapperTest {

    @Test
    fun `AreaDto toDomain should succeed with all fields`() {
        val dto = AreaDto(2088, "England", "ENG", "https://crests.football-data.org/770.svg")
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(2088, result.id)
        assertEquals("England", result.name)
        assertEquals("ENG", result.code)
        assertEquals("https://crests.football-data.org/770.svg", result.flag)
    }

    @Test
    fun `AreaDto toDomain should use defaults when fields are null`() {
        val dto = AreaDto(null, null, null, null)
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(0, result.id)
        assertEquals("", result.name)
        assertEquals("", result.code)
        assertEquals("", result.flag)
    }

    @Test
    fun `SeasonDto toDomain should succeed with all fields`() {
        val dto = SeasonDto(2403, "2025-08-15", "2026-05-24", 26, null)
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(2403, result.id)
        assertEquals("2025-08-15", result.startDate)
        assertEquals("2026-05-24", result.endDate)
        assertEquals(26, result.currentMatchday)
        assertNull(result.winner)
    }

    @Test
    fun `SeasonDto toDomain should allow null currentMatchday`() {
        val dto = SeasonDto(2403, "2025-08-15", "2026-05-24", null, null)
        val result = dto.toDomain()

        assertNotNull(result)
        assertNull(result.currentMatchday)
    }

    @Test
    fun `CompetitionDto toDomain should succeed with all fields`() {
        val dto = CompetitionDto(
            "PL",
            AreaDto(2088, "England", "ENG", "https://crests.football-data.org/770.svg"),
            SeasonDto(2403, "2025-08-15", "2026-05-24", 26, null),
            "https://crests.football-data.org/PL.png",
            "Premier League",
            30,
            "LEAGUE"
        )
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("PL", result.code)
        assertEquals("Premier League", result.name)
        assertEquals("LEAGUE", result.type)
        assertEquals(30, result.numberOfAvailableSeasons)
    }

    @Test
    fun `CompetitionDto toDomain should use defaults when fields are null`() {
        val dto = CompetitionDto(null, null, null, null, null, null, null)
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("", result.code)
        assertEquals("", result.name)
        assertEquals("", result.type)
        assertEquals(0, result.numberOfAvailableSeasons)
        assertNull(result.currentSeason)
    }

    @Test
    fun `TeamDto toDomain should succeed with all fields`() {
        val dto = TeamDto(58, "Aston Villa FC", "Aston Villa", "AVL", "https://crests.football-data.org/58.png")
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(58, result.id)
        assertEquals("Aston Villa FC", result.name)
        assertEquals("Aston Villa", result.shortName)
        assertEquals("AVL", result.tla)
    }

    @Test
    fun `TeamDto toDomain should use defaults when fields are null`() {
        val dto = TeamDto(null, null, null, null, null)
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(0, result.id)
        assertEquals("", result.name)
        assertEquals("", result.shortName)
        assertEquals("", result.tla)
    }

    @Test
    fun `ScoreDto toDomain should succeed with all fields`() {
        val dto = ScoreDto("HOME_TEAM", "REGULAR", ScoreDetailDto(2, 1), ScoreDetailDto(1, 0))
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("HOME_TEAM", result.winner)
        assertEquals("REGULAR", result.duration)
        assertEquals(2, result.fullTime?.home)
        assertEquals(1, result.fullTime?.away)
    }

    @Test
    fun `ScoreDto toDomain should use defaults when duration is null`() {
        val dto = ScoreDto(null, null, ScoreDetailDto(null, null), ScoreDetailDto(null, null))
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals("REGULAR", result.duration)
        assertNull(result.winner)
    }

    @Test
    fun `MatchDto toDomain should succeed with all fields`() {
        val dto = MatchDto(
            538036,
            TeamDto(397, "Brighton & Hove Albion FC", "Brighton Hove", "BHA", null),
            MatchCompetitionDto(2021, "Premier League", "PL", "LEAGUE", null),
            TeamDto(58, "Aston Villa FC", "Aston Villa", "AVL", null),
            26,
            ScoreDto(null, "REGULAR", ScoreDetailDto(null, null), ScoreDetailDto(null, null)),
            SeasonDto(2403, "2025-08-15", "2026-05-24", 26, null),
            "REGULAR_SEASON",
            "TIMED",
            "2026-02-11T19:30:00Z"
        )
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(538036, result.id)
        assertEquals("Aston Villa FC", result.homeTeam?.name)
        assertEquals("Brighton & Hove Albion FC", result.awayTeam?.name)
        assertEquals(26, result.matchday)
    }

    @Test
    fun `MatchDto toDomain should use defaults when fields are null`() {
        val dto = MatchDto(null, null, null, null, null, null, null, null, null, null)
        val result = dto.toDomain()

        assertNotNull(result)
        assertEquals(0, result.id)
        assertEquals(0, result.matchday)
        assertEquals("UNKNOWN", result.stage)
        assertEquals("SCHEDULED", result.status)
    }
}


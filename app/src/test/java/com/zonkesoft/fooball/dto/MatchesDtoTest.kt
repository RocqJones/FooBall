package com.zonkesoft.fooball.dto

import com.google.gson.Gson
import com.zonkesoft.fooball.data_source.remote.dto.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MatchesDtoTest {

    private lateinit var gson: Gson

    @Before
    fun setup() {
        gson = Gson()
    }

    @Test
    fun `MatchesResponse should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "status": "success",
                "count": 2,
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

        // When
        val response = gson.fromJson(json, MatchesResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals("success", response.status)
        assertEquals(2, response.count)
        assertEquals("PL", response.competition?.code)
        assertEquals("Premier League", response.competition?.name)
        assertEquals(1, response.matches?.size)

        val match = response.matches?.first()
        assertEquals(538036, match?.id)
        assertEquals(26, match?.matchday)
        assertEquals("REGULAR_SEASON", match?.stage)
        assertEquals("TIMED", match?.status)
        assertEquals("2026-02-11T19:30:00Z", match?.utcDate)
    }

    @Test
    fun `MatchDto should deserialize correctly from JSON`() {
        // Given
        val json = """
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
        """.trimIndent()

        // When
        val match = gson.fromJson(json, MatchDto::class.java)

        // Then
        assertNotNull(match)
        assertEquals(538036, match.id)
        assertEquals(26, match.matchday)
        assertEquals("REGULAR_SEASON", match.stage)
        assertEquals("TIMED", match.status)
        assertEquals("2026-02-11T19:30:00Z", match.utcDate)

        assertNotNull(match.homeTeam)
        assertEquals(58, match.homeTeam?.id)
        assertEquals("Aston Villa FC", match.homeTeam?.name)

        assertNotNull(match.awayTeam)
        assertEquals(397, match.awayTeam?.id)
        assertEquals("Brighton & Hove Albion FC", match.awayTeam?.name)

        assertNotNull(match.score)
        assertNull(match.score?.winner)
        assertEquals("REGULAR", match.score?.duration)
    }

    @Test
    fun `TeamDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "id": 58,
                "name": "Aston Villa FC",
                "shortName": "Aston Villa",
                "tla": "AVL",
                "crest": "https://crests.football-data.org/58.png"
            }
        """.trimIndent()

        // When
        val team = gson.fromJson(json, TeamDto::class.java)

        // Then
        assertNotNull(team)
        assertEquals(58, team.id)
        assertEquals("Aston Villa FC", team.name)
        assertEquals("Aston Villa", team.shortName)
        assertEquals("AVL", team.tla)
        assertEquals("https://crests.football-data.org/58.png", team.crest)
    }

    @Test
    fun `ScoreDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "winner": "HOME_TEAM",
                "duration": "REGULAR",
                "fullTime": {
                    "home": 2,
                    "away": 1
                },
                "halfTime": {
                    "home": 1,
                    "away": 0
                }
            }
        """.trimIndent()

        // When
        val score = gson.fromJson(json, ScoreDto::class.java)

        // Then
        assertNotNull(score)
        assertEquals("HOME_TEAM", score.winner)
        assertEquals("REGULAR", score.duration)

        assertNotNull(score.fullTime)
        assertEquals(2, score.fullTime?.home)
        assertEquals(1, score.fullTime?.away)

        assertNotNull(score.halfTime)
        assertEquals(1, score.halfTime?.home)
        assertEquals(0, score.halfTime?.away)
    }

    @Test
    fun `MatchCompetitionDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "id": 2021,
                "name": "Premier League",
                "code": "PL",
                "type": "LEAGUE",
                "emblem": "https://crests.football-data.org/PL.png"
            }
        """.trimIndent()

        // When
        val competition = gson.fromJson(json, MatchCompetitionDto::class.java)

        // Then
        assertNotNull(competition)
        assertEquals(2021, competition.id)
        assertEquals("Premier League", competition.name)
        assertEquals("PL", competition.code)
        assertEquals("LEAGUE", competition.type)
        assertEquals("https://crests.football-data.org/PL.png", competition.emblem)
    }

    @Test
    fun `MatchesResponse should handle null fields correctly`() {
        // Given
        val json = """
            {
                "status": null,
                "count": null,
                "competition": null,
                "matches": null
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, MatchesResponse::class.java)

        // Then
        assertNotNull(response)
        assertNull(response.status)
        assertNull(response.count)
        assertNull(response.competition)
        assertNull(response.matches)
    }

    @Test
    fun `MatchesResponse should serialize correctly to JSON`() {
        // Given
        val response = MatchesResponse(
            status = "success",
            count = 1,
            competition = CompetitionInfoDto(
                code = "PL",
                name = "Premier League"
            ),
            matches = listOf(
                MatchDto(
                    id = 538036,
                    awayTeam = TeamDto(
                        id = 397,
                        name = "Brighton & Hove Albion FC",
                        shortName = "Brighton Hove",
                        tla = "BHA",
                        crest = "https://crests.football-data.org/397.png"
                    ),
                    competition = MatchCompetitionDto(
                        id = 2021,
                        name = "Premier League",
                        code = "PL",
                        type = "LEAGUE",
                        emblem = "https://crests.football-data.org/PL.png"
                    ),
                    homeTeam = TeamDto(
                        id = 58,
                        name = "Aston Villa FC",
                        shortName = "Aston Villa",
                        tla = "AVL",
                        crest = "https://crests.football-data.org/58.png"
                    ),
                    matchday = 26,
                    score = ScoreDto(
                        winner = null,
                        duration = "REGULAR",
                        fullTime = ScoreDetailDto(home = null, away = null),
                        halfTime = ScoreDetailDto(home = null, away = null)
                    ),
                    season = SeasonDto(
                        id = 2403,
                        startDate = "2025-08-15",
                        endDate = "2026-05-24",
                        currentMatchday = 26,
                        winner = null
                    ),
                    stage = "REGULAR_SEASON",
                    status = "TIMED",
                    utcDate = "2026-02-11T19:30:00Z"
                )
            )
        )

        // When
        val json = gson.toJson(response)
        val deserialized = gson.fromJson(json, MatchesResponse::class.java)

        // Then
        assertNotNull(json)
        assertEquals(response.status, deserialized.status)
        assertEquals(response.count, deserialized.count)
        assertEquals(response.matches?.size, deserialized.matches?.size)
    }

    @Test
    fun `ScoreDetailDto should handle null scores`() {
        // Given
        val json = """
            {
                "home": null,
                "away": null
            }
        """.trimIndent()

        // When
        val scoreDetail = gson.fromJson(json, ScoreDetailDto::class.java)

        // Then
        assertNotNull(scoreDetail)
        assertNull(scoreDetail.home)
        assertNull(scoreDetail.away)
    }
}


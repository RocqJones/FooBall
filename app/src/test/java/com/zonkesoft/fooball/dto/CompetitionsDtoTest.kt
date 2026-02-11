package com.zonkesoft.fooball.dto

import com.google.gson.Gson
import com.zonkesoft.fooball.data_source.remote.dto.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CompetitionsDtoTest {

    private lateinit var gson: Gson

    @Before
    fun setup() {
        gson = Gson()
    }

    @Test
    fun `CompetitionsResponse should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "status": "success",
                "count": 2,
                "competitions": [
                    {
                        "code": "PL",
                        "area": {
                            "id": 2088,
                            "name": "England",
                            "code": "ENG",
                            "flag": "https://crests.football-data.org/770.svg"
                        },
                        "currentSeason": {
                            "id": 2403,
                            "startDate": "2025-08-15",
                            "endDate": "2026-05-24",
                            "currentMatchday": 26,
                            "winner": null
                        },
                        "emblem": "https://crests.football-data.org/PL.png",
                        "name": "Premier League",
                        "numberOfAvailableSeasons": 30,
                        "type": "LEAGUE"
                    },
                    {
                        "code": "BL1",
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
                        "emblem": "https://crests.football-data.org/BL1.png",
                        "name": "Bundesliga",
                        "numberOfAvailableSeasons": 30,
                        "type": "LEAGUE"
                    }
                ]
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, CompetitionsResponse::class.java)

        // Then
        assertNotNull(response)
        assertEquals("success", response.status)
        assertEquals(2, response.count)
        assertEquals(2, response.competitions?.size)

        val firstCompetition = response.competitions?.first()
        assertEquals("PL", firstCompetition?.code)
        assertEquals("Premier League", firstCompetition?.name)
        assertEquals("LEAGUE", firstCompetition?.type)
        assertEquals(30, firstCompetition?.numberOfAvailableSeasons)
    }

    @Test
    fun `CompetitionDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "code": "PL",
                "area": {
                    "id": 2088,
                    "name": "England",
                    "code": "ENG",
                    "flag": "https://crests.football-data.org/770.svg"
                },
                "currentSeason": {
                    "id": 2403,
                    "startDate": "2025-08-15",
                    "endDate": "2026-05-24",
                    "currentMatchday": 26,
                    "winner": null
                },
                "emblem": "https://crests.football-data.org/PL.png",
                "name": "Premier League",
                "numberOfAvailableSeasons": 30,
                "type": "LEAGUE"
            }
        """.trimIndent()

        // When
        val competition = gson.fromJson(json, CompetitionDto::class.java)

        // Then
        assertNotNull(competition)
        assertEquals("PL", competition.code)
        assertEquals("Premier League", competition.name)
        assertEquals("LEAGUE", competition.type)
        assertEquals("https://crests.football-data.org/PL.png", competition.emblem)
        assertEquals(30, competition.numberOfAvailableSeasons)

        assertNotNull(competition.area)
        assertEquals(2088, competition.area?.id)
        assertEquals("England", competition.area?.name)

        assertNotNull(competition.currentSeason)
        assertEquals(2403, competition.currentSeason?.id)
        assertEquals(26, competition.currentSeason?.currentMatchday)
    }

    @Test
    fun `AreaDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "id": 2088,
                "name": "England",
                "code": "ENG",
                "flag": "https://crests.football-data.org/770.svg"
            }
        """.trimIndent()

        // When
        val area = gson.fromJson(json, AreaDto::class.java)

        // Then
        assertNotNull(area)
        assertEquals(2088, area.id)
        assertEquals("England", area.name)
        assertEquals("ENG", area.code)
        assertEquals("https://crests.football-data.org/770.svg", area.flag)
    }

    @Test
    fun `SeasonDto should deserialize correctly from JSON`() {
        // Given
        val json = """
            {
                "id": 2403,
                "startDate": "2025-08-15",
                "endDate": "2026-05-24",
                "currentMatchday": 26,
                "winner": null
            }
        """.trimIndent()

        // When
        val season = gson.fromJson(json, SeasonDto::class.java)

        // Then
        assertNotNull(season)
        assertEquals(2403, season.id)
        assertEquals("2025-08-15", season.startDate)
        assertEquals("2026-05-24", season.endDate)
        assertEquals(26, season.currentMatchday)
        assertNull(season.winner)
    }

    @Test
    fun `CompetitionsResponse should handle null fields correctly`() {
        // Given
        val json = """
            {
                "status": null,
                "count": null,
                "competitions": null
            }
        """.trimIndent()

        // When
        val response = gson.fromJson(json, CompetitionsResponse::class.java)

        // Then
        assertNotNull(response)
        assertNull(response.status)
        assertNull(response.count)
        assertNull(response.competitions)
    }

    @Test
    fun `CompetitionsResponse should serialize correctly to JSON`() {
        // Given
        val response = CompetitionsResponse(
            status = "success",
            count = 1,
            competitions = listOf(
                CompetitionDto(
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
            )
        )

        // When
        val json = gson.toJson(response)
        val deserialized = gson.fromJson(json, CompetitionsResponse::class.java)

        // Then
        assertNotNull(json)
        assertEquals(response.status, deserialized.status)
        assertEquals(response.count, deserialized.count)
        assertEquals(response.competitions?.size, deserialized.competitions?.size)
    }
}


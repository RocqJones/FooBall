package com.zonkesoft.fooball.domain.model

/**
 * Domain model for a competition
 */
data class Competition(
    val code: String?,
    val area: Area?,
    val currentSeason: Season?,
    val emblem: String?,
    val name: String?,
    val numberOfAvailableSeasons: Int?,
    val type: String?
)

/**
 * Domain model for an area/country
 */
data class Area(
    val id: Int?,
    val name: String?,
    val code: String?,
    val flag: String?
)

/**
 * Domain model for a season
 */
data class Season(
    val id: Int,
    val startDate: String,
    val endDate: String,
    val currentMatchday: Int?,
    val winner: Winner?
)

/**
 * Domain model for a competition winner (team)
 */
data class Winner(
    val id: Int?,
    val name: String?,
    val shortName: String?,
    val tla: String?,
    val crest: String?
)

/**
 * Domain model for a match
 */
data class Match(
    val id: Int?,
    val awayTeam: Team?,
    val competition: MatchCompetition?,
    val homeTeam: Team?,
    val matchday: Int?, // 0 indicates no matchday info
    val score: Score?,
    val season: Season?,
    val stage: String?, // "UNKNOWN" if not specified
    val status: String?, // "SCHEDULED" as default
    val utcDate: String?
)

/**
 * Domain model for a team
 */
data class Team(
    val id: Int?,
    val name: String?,
    val shortName: String?,
    val tla: String?,
    val crest: String?
)

/**
 * Domain model for competition info in a match
 */
data class MatchCompetition(
    val id: Int?,
    val name: String?,
    val code: String?,
    val type: String?,
    val emblem: String?
)

/**
 * Domain model for a match score
 */
data class Score(
    val winner: String?,
    val duration: String?,
    val fullTime: ScoreDetail?,
    val halfTime: ScoreDetail?
)

/**
 * Domain model for score details
 */
data class ScoreDetail(
    val home: Int?,
    val away: Int?
)


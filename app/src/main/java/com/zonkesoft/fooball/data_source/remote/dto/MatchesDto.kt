package com.zonkesoft.fooball.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class MatchesRequest(
    val competition_code: String
)

data class MatchesResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("competition")
    val competition: CompetitionInfoDto?,
    @SerializedName("matches")
    val matches: List<MatchDto>?
)

data class CompetitionInfoDto(
    @SerializedName("code")
    val code: String?,
    @SerializedName("name")
    val name: String?
)

data class MatchDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("awayTeam")
    val awayTeam: TeamDto?,
    @SerializedName("competition")
    val competition: MatchCompetitionDto?,
    @SerializedName("homeTeam")
    val homeTeam: TeamDto?,
    @SerializedName("matchday")
    val matchday: Int?,
    @SerializedName("score")
    val score: ScoreDto?,
    @SerializedName("season")
    val season: SeasonDto?,
    @SerializedName("stage")
    val stage: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("utcDate")
    val utcDate: String?
)

data class TeamDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("shortName")
    val shortName: String?,
    @SerializedName("tla")
    val tla: String?,
    @SerializedName("crest")
    val crest: String?
)

data class MatchCompetitionDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("emblem")
    val emblem: String?
)

data class ScoreDto(
    @SerializedName("winner")
    val winner: String?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("fullTime")
    val fullTime: ScoreDetailDto?,
    @SerializedName("halfTime")
    val halfTime: ScoreDetailDto?
)

data class ScoreDetailDto(
    @SerializedName("home")
    val home: Int?,
    @SerializedName("away")
    val away: Int?
)


package com.zonkesoft.fooball.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class CompetitionsResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("competitions")
    val competitions: List<CompetitionDto>?
)

data class CompetitionDto(
    @SerializedName("code")
    val code: String?,
    @SerializedName("area")
    val area: AreaDto?,
    @SerializedName("currentSeason")
    val currentSeason: SeasonDto?,
    @SerializedName("emblem")
    val emblem: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("numberOfAvailableSeasons")
    val numberOfAvailableSeasons: Int?,
    @SerializedName("type")
    val type: String?
)

data class AreaDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("flag")
    val flag: String?
)

data class SeasonDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("currentMatchday")
    val currentMatchday: Int?,
    @SerializedName("winner")
    val winner: WinnerDto? = null
)

data class WinnerDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("shortName")
    val shortName: String?,
    @SerializedName("tla")
    val tla: String?,
    @SerializedName("crest")
    val crest: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("website")
    val website: String?,
    @SerializedName("founded")
    val founded: Int?,
    @SerializedName("clubColors")
    val clubColors: String?,
    @SerializedName("venue")
    val venue: String?
)


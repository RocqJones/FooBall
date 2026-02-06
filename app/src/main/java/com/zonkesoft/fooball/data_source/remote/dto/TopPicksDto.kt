package com.zonkesoft.fooball.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class TopPicksResponse(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("top_picks")
    val topPicks: List<TopPickDto>?
)

data class TopPickDto(
    @SerializedName("fixture_id")
    val fixtureId: Int?,
    @SerializedName("match")
    val match: String?,
    @SerializedName("league")
    val league: String?,
    @SerializedName("league_logo")
    val leagueLogo: String?,
    @SerializedName("league_flag")
    val leagueFlag: String?,
    @SerializedName("home_team_logo")
    val homeTeamLogo: String?,
    @SerializedName("away_team_logo")
    val awayTeamLogo: String?,
    @SerializedName("home_win_probability")
    val homeWinProbability: Double?,
    @SerializedName("draw_probability")
    val drawProbability: Double?,
    @SerializedName("away_win_probability")
    val awayWinProbability: Double?,
    @SerializedName("goals_prediction")
    val goalsPrediction: GoalsPredictionDto?,
    @SerializedName("btts_probability")
    val bttsProbability: Double?,
    @SerializedName("composite_score")
    val compositeScore: Double?,
    @SerializedName("created_at")
    val createdAt: String?
)
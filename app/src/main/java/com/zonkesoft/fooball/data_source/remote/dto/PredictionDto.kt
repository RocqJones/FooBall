package com.zonkesoft.fooball.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class TodayPredictionsResponse(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("count")
    val count: Int?,
    @SerializedName("predictions")
    val predictions: List<PredictionDto>?
)

data class PredictionDto(
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
    @SerializedName("home_team")
    val homeTeam: String?,
    @SerializedName("home_team_logo")
    val homeTeamLogo: String?,
    @SerializedName("away_team")
    val awayTeam: String?,
    @SerializedName("away_team_logo")
    val awayTeamLogo: String?,
    @SerializedName("home_win_probability")
    val homeWinProbability: Double?,
    @SerializedName("home_win_confidence")
    val homeWinConfidence: String?,
    @SerializedName("away_win_probability")
    val awayWinProbability: Double?,
    @SerializedName("away_win_confidence")
    val awayWinConfidence: String?,
    @SerializedName("draw_probability")
    val drawProbability: Double?,
    @SerializedName("draw_confidence")
    val drawConfidence: String?,
    @SerializedName("predicted_outcome")
    val predictedOutcome: String?,
    @SerializedName("predicted_outcome_probability")
    val predictedOutcomeProbability: Double?,
    @SerializedName("goals_prediction")
    val goalsPrediction: GoalsPredictionDto?,
    @SerializedName("btts_probability")
    val bttsProbability: Double?,
    @SerializedName("btts_confidence")
    val bttsConfidence: String?,
    @SerializedName("value_score")
    val valueScore: Double? = null,
    @SerializedName("created_at")
    val createdAt: String?
)

data class GoalsPredictionDto(
    @SerializedName("bet")
    val bet: String?,
    @SerializedName("probability")
    val probability: Double?,
    @SerializedName("confidence")
    val confidence: String?
)
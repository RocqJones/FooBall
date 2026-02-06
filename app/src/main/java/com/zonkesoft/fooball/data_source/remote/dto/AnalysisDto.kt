package com.zonkesoft.fooball.data_source.remote.dto

import com.google.gson.annotations.SerializedName

data class AnalysisResponse(
    @SerializedName("statusCode")
    val statusCode: Int?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("total_predictions")
    val totalPredictions: Int?,
    @SerializedName("best_home_wins")
    val bestHomeWins: List<BestHomeWinDto>?,
    @SerializedName("best_goals_bets")
    val bestGoalsBets: List<BestGoalsBetDto>?,
    @SerializedName("best_btts")
    val bestBtts: List<BestBttsDto>?,
    @SerializedName("best_value_bets")
    val bestValueBets: List<BestValueBetDto>?,
    @SerializedName("summary")
    val summary: SummaryDto?
)

data class BestHomeWinDto(
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
    @SerializedName("home_win_confidence")
    val homeWinConfidence: String?,
    @SerializedName("draw_probability")
    val drawProbability: Double?,
    @SerializedName("away_win_probability")
    val awayWinProbability: Double?,
    @SerializedName("value_score")
    val valueScore: Double? = null
)

data class BestGoalsBetDto(
    @SerializedName("match")
    val match: String?,
    @SerializedName("league_logo")
    val leagueLogo: String?,
    @SerializedName("league_flag")
    val leagueFlag: String?,
    @SerializedName("home_team_logo")
    val homeTeamLogo: String?,
    @SerializedName("away_team_logo")
    val awayTeamLogo: String?,
    @SerializedName("goals_prediction")
    val goalsPrediction: GoalsPredictionDto?
)

data class BestBttsDto(
    @SerializedName("match")
    val match: String?,
    @SerializedName("league_logo")
    val leagueLogo: String?,
    @SerializedName("league_flag")
    val leagueFlag: String?,
    @SerializedName("home_team_logo")
    val homeTeamLogo: String?,
    @SerializedName("away_team_logo")
    val awayTeamLogo: String?,
    @SerializedName("btts_probability")
    val bttsProbability: Double?,
    @SerializedName("btts_confidence")
    val bttsConfidence: String?
)

data class BestValueBetDto(
    @SerializedName("match")
    val match: String?,
    @SerializedName("league_logo")
    val leagueLogo: String?,
    @SerializedName("league_flag")
    val leagueFlag: String?,
    @SerializedName("home_team_logo")
    val homeTeamLogo: String?,
    @SerializedName("away_team_logo")
    val awayTeamLogo: String?,
    @SerializedName("value_score")
    val valueScore: Double?,
    @SerializedName("predicted_outcome")
    val predictedOutcome: String?,
    @SerializedName("probability")
    val probability: Double?
)

data class SummaryDto(
    @SerializedName("total_matches")
    val totalMatches: Int?,
    @SerializedName("high_confidence_home_wins")
    val highConfidenceHomeWins: Int?,
    @SerializedName("over_2_5_count")
    val over25Count: Int?,
    @SerializedName("under_2_5_count")
    val under25Count: Int?,
    @SerializedName("avg_home_win_probability")
    val avgHomeWinProbability: Double?,
    @SerializedName("avg_btts_probability")
    val avgBttsProbability: Double?,
    @SerializedName("high_confidence_goals_bets")
    val highConfidenceGoalsBets: Int?,
    @SerializedName("matches_by_league")
    val matchesByLeague: Map<String, Int>?
)
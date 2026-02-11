package com.zonkesoft.fooball.domain.model

/**
 * Domain model for a prediction
 */
data class Prediction(
    val fixtureId: Int?,
    val match: String?,
    val league: String?,
    val leagueLogo: String?,
    val leagueFlag: String?,
    val homeTeam: String?,
    val homeTeamLogo: String?,
    val awayTeam: String?,
    val awayTeamLogo: String?,
    val homeWinProbability: Double?,
    val homeWinConfidence: String?,
    val awayWinProbability: Double?,
    val awayWinConfidence: String?,
    val drawProbability: Double?,
    val drawConfidence: String?,
    val predictedOutcome: String?,
    val predictedOutcomeProbability: Double?,
    val goalsPrediction: GoalsPrediction?,
    val bttsProbability: Double?,
    val bttsConfidence: String?,
    val valueScore: Double? = null,
    val createdAt: String?
)

/**
 * Domain model for goals prediction
 */
data class GoalsPrediction(
    val bet: String?,
    val probability: Double?,
    val confidence: String?
)

/**
 * Domain model for top pick
 */
data class TopPick(
    val fixtureId: Int?,
    val match: String?,
    val league: String?,
    val leagueLogo: String?,
    val leagueFlag: String?,
    val homeTeamLogo: String?,
    val awayTeamLogo: String?,
    val homeWinProbability: Double?,
    val drawProbability: Double?,
    val awayWinProbability: Double?,
    val goalsPrediction: GoalsPrediction?,
    val bttsProbability: Double?,
    val compositeScore: Double?,
    val createdAt: String?
)
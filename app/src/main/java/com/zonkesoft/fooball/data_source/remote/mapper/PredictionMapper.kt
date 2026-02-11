package com.zonkesoft.fooball.data_source.remote.mapper

import com.zonkesoft.fooball.data_source.remote.dto.GoalsPredictionDto
import com.zonkesoft.fooball.data_source.remote.dto.PredictionDto
import com.zonkesoft.fooball.data_source.remote.dto.TopPickDto
import com.zonkesoft.fooball.domain.model.GoalsPrediction
import com.zonkesoft.fooball.domain.model.Prediction
import com.zonkesoft.fooball.domain.model.TopPick

/**
 * Exception thrown when required fields in DTO are null during mapping.
 * The exception is designed to integrate with the existing Result-based error handling pattern in the repository layer
 */
class InvalidDataException(message: String) : IllegalArgumentException(message)

fun GoalsPredictionDto.toDomain(): GoalsPrediction {
    return GoalsPrediction(
        bet = bet.orEmpty(),
        probability = probability ?: 0.0,
        confidence = confidence.orEmpty()
    )
}

fun PredictionDto.toDomain(): Prediction {
    return Prediction(
        fixtureId = fixtureId ?: 0,
        match = match.orEmpty(),
        league = league.orEmpty(),
        leagueLogo = leagueLogo.orEmpty(),
        leagueFlag = leagueFlag.orEmpty(),
        homeTeam = homeTeam.orEmpty(),
        homeTeamLogo = homeTeamLogo.orEmpty(),
        awayTeam = awayTeam.orEmpty(),
        awayTeamLogo = awayTeamLogo.orEmpty(),
        homeWinProbability = homeWinProbability ?: 0.0,
        homeWinConfidence = homeWinConfidence.orEmpty(),
        awayWinProbability = awayWinProbability ?: 0.0,
        awayWinConfidence = awayWinConfidence.orEmpty(),
        drawProbability = drawProbability ?: 0.0,
        drawConfidence = drawConfidence.orEmpty(),
        predictedOutcome = predictedOutcome.orEmpty(),
        predictedOutcomeProbability = predictedOutcomeProbability ?: 0.0,
        goalsPrediction = goalsPrediction?.toDomain() ?: GoalsPrediction("", 0.0, ""),
        bttsProbability = bttsProbability ?: 0.0,
        bttsConfidence = bttsConfidence.orEmpty(),
        valueScore = valueScore,
        createdAt = createdAt.orEmpty()
    )
}

fun TopPickDto.toDomain(): TopPick {
    return TopPick(
        fixtureId = fixtureId ?: 0,
        match = match.orEmpty(),
        league = league.orEmpty(),
        leagueLogo = leagueLogo.orEmpty(),
        leagueFlag = leagueFlag.orEmpty(),
        homeTeamLogo = homeTeamLogo.orEmpty(),
        awayTeamLogo = awayTeamLogo.orEmpty(),
        homeWinProbability = homeWinProbability ?: 0.0,
        drawProbability = drawProbability ?: 0.0,
        awayWinProbability = awayWinProbability ?: 0.0,
        goalsPrediction = goalsPrediction?.toDomain() ?: GoalsPrediction("", 0.0, ""),
        bttsProbability = bttsProbability ?: 0.0,
        compositeScore = compositeScore ?: 0.0,
        createdAt = createdAt.orEmpty()
    )
}
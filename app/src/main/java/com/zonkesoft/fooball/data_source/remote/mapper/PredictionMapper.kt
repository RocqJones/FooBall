package com.zonkesoft.fooball.data_source.remote.mapper

import com.zonkesoft.fooball.data_source.remote.dto.AnalysisResponse
import com.zonkesoft.fooball.data_source.remote.dto.BestBttsDto
import com.zonkesoft.fooball.data_source.remote.dto.BestHomeWinDto
import com.zonkesoft.fooball.data_source.remote.dto.GoalsPredictionDto
import com.zonkesoft.fooball.data_source.remote.dto.PredictionDto
import com.zonkesoft.fooball.data_source.remote.dto.SummaryDto
import com.zonkesoft.fooball.data_source.remote.dto.TopPickDto
import com.zonkesoft.fooball.domain.model.AnalysisSummary
import com.zonkesoft.fooball.domain.model.BestBtts
import com.zonkesoft.fooball.domain.model.BestHomeWin
import com.zonkesoft.fooball.domain.model.GoalsPrediction
import com.zonkesoft.fooball.domain.model.Prediction
import com.zonkesoft.fooball.domain.model.PredictionsAnalysis
import com.zonkesoft.fooball.domain.model.TopPick

/**
 * Exception thrown when required fields in DTO are null during mapping.
 * 
 * This exception is thrown by mapper functions when critical fields that should never be null
 * are encountered as null in the DTO. The exception is designed to integrate with the existing
 * Result-based error handling pattern in the repository layer, where it will be caught and
 * converted to Result.Error, properly informing the caller about data quality issues.
 */
class InvalidDataException(message: String) : IllegalArgumentException(message)

fun GoalsPredictionDto.toDomain(): GoalsPrediction {
    return GoalsPrediction(
        bet = bet ?: throw InvalidDataException("GoalsPredictionDto.bet cannot be null"),
        probability = probability ?: throw InvalidDataException("GoalsPredictionDto.probability cannot be null"),
        confidence = confidence ?: throw InvalidDataException("GoalsPredictionDto.confidence cannot be null")
    )
}

fun PredictionDto.toDomain(): Prediction {
    return Prediction(
        fixtureId = fixtureId ?: throw InvalidDataException("PredictionDto.fixtureId cannot be null"),
        match = match ?: throw InvalidDataException("PredictionDto.match cannot be null"),
        league = league ?: "",
        leagueLogo = leagueLogo ?: "",
        leagueFlag = leagueFlag ?: "",
        homeTeam = homeTeam ?: throw InvalidDataException("PredictionDto.homeTeam cannot be null"),
        homeTeamLogo = homeTeamLogo ?: "",
        awayTeam = awayTeam ?: throw InvalidDataException("PredictionDto.awayTeam cannot be null"),
        awayTeamLogo = awayTeamLogo ?: "",
        homeWinProbability = homeWinProbability ?: 0.0,
        homeWinConfidence = homeWinConfidence ?: "",
        awayWinProbability = awayWinProbability ?: 0.0,
        awayWinConfidence = awayWinConfidence ?: "",
        drawProbability = drawProbability ?: 0.0,
        drawConfidence = drawConfidence ?: "",
        predictedOutcome = predictedOutcome ?: "",
        predictedOutcomeProbability = predictedOutcomeProbability ?: 0.0,
        goalsPrediction = goalsPrediction?.toDomain() ?: GoalsPrediction("", 0.0, ""),
        bttsProbability = bttsProbability ?: 0.0,
        bttsConfidence = bttsConfidence ?: "",
        valueScore = valueScore,
        createdAt = createdAt ?: ""
    )
}

fun TopPickDto.toDomain(): TopPick {
    return TopPick(
        fixtureId = fixtureId ?: throw InvalidDataException("TopPickDto.fixtureId cannot be null"),
        match = match ?: throw InvalidDataException("TopPickDto.match cannot be null"),
        league = league ?: "",
        leagueLogo = leagueLogo ?: "",
        leagueFlag = leagueFlag ?: "",
        homeTeamLogo = homeTeamLogo ?: "",
        awayTeamLogo = awayTeamLogo ?: "",
        homeWinProbability = homeWinProbability ?: 0.0,
        drawProbability = drawProbability ?: 0.0,
        awayWinProbability = awayWinProbability ?: 0.0,
        goalsPrediction = goalsPrediction?.toDomain() ?: GoalsPrediction("", 0.0, ""),
        bttsProbability = bttsProbability ?: 0.0,
        compositeScore = compositeScore ?: 0.0,
        createdAt = createdAt ?: ""
    )
}

fun BestHomeWinDto.toDomain(): BestHomeWin {
    return BestHomeWin(
        match = match ?: throw InvalidDataException("BestHomeWinDto.match cannot be null"),
        league = league ?: "",
        leagueLogo = leagueLogo ?: "",
        leagueFlag = leagueFlag ?: "",
        homeTeamLogo = homeTeamLogo ?: "",
        awayTeamLogo = awayTeamLogo ?: "",
        homeWinProbability = homeWinProbability ?: 0.0,
        homeWinConfidence = homeWinConfidence ?: "",
        drawProbability = drawProbability ?: 0.0,
        awayWinProbability = awayWinProbability ?: 0.0,
        valueScore = valueScore
    )
}

fun BestBttsDto.toDomain(): BestBtts {
    return BestBtts(
        match = match ?: throw InvalidDataException("BestBttsDto.match cannot be null"),
        leagueLogo = leagueLogo ?: "",
        leagueFlag = leagueFlag ?: "",
        homeTeamLogo = homeTeamLogo ?: "",
        awayTeamLogo = awayTeamLogo ?: "",
        bttsProbability = bttsProbability ?: 0.0,
        bttsConfidence = bttsConfidence ?: ""
    )
}

fun SummaryDto.toDomain(): AnalysisSummary {
    return AnalysisSummary(
        totalMatches = totalMatches ?: 0,
        highConfidenceHomeWins = highConfidenceHomeWins ?: 0,
        over25Count = over25Count ?: 0,
        under25Count = under25Count ?: 0,
        avgHomeWinProbability = avgHomeWinProbability ?: 0.0,
        avgBttsProbability = avgBttsProbability ?: 0.0,
        highConfidenceGoalsBets = highConfidenceGoalsBets ?: 0,
        matchesByLeague = matchesByLeague ?: emptyMap()
    )
}

fun AnalysisResponse.toDomain(): PredictionsAnalysis {
    return PredictionsAnalysis(
        totalPredictions = totalPredictions ?: 0,
        bestHomeWins = bestHomeWins?.map { it.toDomain() } ?: emptyList(),
        bestBtts = bestBtts?.map { it.toDomain() } ?: emptyList(),
        summary = summary?.toDomain() ?: AnalysisSummary(0, 0, 0, 0, 0.0, 0.0, 0, emptyMap())
    )
}
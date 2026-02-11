package com.zonkesoft.fooball.data_source.remote.mapper

import com.zonkesoft.fooball.data_source.remote.dto.*
import com.zonkesoft.fooball.domain.model.*

fun AreaDto.toDomain(): Area {
    return Area(
        id = id ?: 0,
        name = name.orEmpty(),
        code = code.orEmpty(),
        flag = flag.orEmpty()
    )
}

fun WinnerDto.toDomain(): Winner {
    return Winner(
        id = id ?: 0,
        name = name.orEmpty(),
        shortName = shortName.orEmpty(),
        tla = tla.orEmpty(),
        crest = crest.orEmpty()
    )
}

fun SeasonDto.toDomain(): Season {
    return Season(
        id = id ?: 0,
        startDate = startDate.orEmpty(),
        endDate = endDate.orEmpty(),
        currentMatchday = currentMatchday,
        winner = winner?.toDomain()
    )
}

fun CompetitionDto.toDomain(): Competition {
    return Competition(
        code = code.orEmpty(),
        area = area?.toDomain() ?: Area(0, "", "", ""),
        currentSeason = currentSeason?.toDomain(),
        emblem = emblem.orEmpty(),
        name = name.orEmpty(),
        numberOfAvailableSeasons = numberOfAvailableSeasons ?: 0,
        type = type.orEmpty()
    )
}

fun TeamDto.toDomain(): Team {
    return Team(
        id = id ?: 0,
        name = name.orEmpty(),
        shortName = shortName.orEmpty(),
        tla = tla.orEmpty(),
        crest = crest.orEmpty()
    )
}

fun MatchCompetitionDto.toDomain(): MatchCompetition {
    return MatchCompetition(
        id = id ?: 0,
        name = name.orEmpty(),
        code = code.orEmpty(),
        type = type.orEmpty(),
        emblem = emblem.orEmpty()
    )
}

fun ScoreDetailDto.toDomain(): ScoreDetail {
    return ScoreDetail(
        home = home,
        away = away
    )
}

fun ScoreDto.toDomain(): Score {
    return Score(
        winner = winner,
        duration = duration ?: "REGULAR",
        fullTime = fullTime?.toDomain() ?: ScoreDetail(null, null),
        halfTime = halfTime?.toDomain() ?: ScoreDetail(null, null)
    )
}

fun MatchDto.toDomain(): Match {
    return Match(
        id = id ?: 0,
        awayTeam = awayTeam?.toDomain() ?: Team(0, "", "", "", ""),
        competition = competition?.toDomain() ?: MatchCompetition(0, "", "", "", ""),
        homeTeam = homeTeam?.toDomain() ?: Team(0, "", "", "", ""),
        matchday = matchday ?: 0,
        score = score?.toDomain() ?: Score(null, "REGULAR", ScoreDetail(null, null), ScoreDetail(null, null)),
        season = season?.toDomain() ?: Season(0, "", "", null, null),
        stage = stage ?: "UNKNOWN",
        status = status ?: "SCHEDULED",
        utcDate = utcDate.orEmpty()
    )
}


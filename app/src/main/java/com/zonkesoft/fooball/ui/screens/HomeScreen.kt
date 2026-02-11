package com.zonkesoft.fooball.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.zonkesoft.fooball.R
import com.zonkesoft.fooball.domain.model.Competition
import com.zonkesoft.fooball.ui.components.NetworkAwareContent
import com.zonkesoft.fooball.ui.components.OfflineContent
import com.zonkesoft.fooball.ui.components.TextBold
import com.zonkesoft.fooball.ui.components.TextMedium
import com.zonkesoft.fooball.ui.components.TextRegular
import com.zonkesoft.fooball.ui.viewmodel.HomeUiState

@Composable
fun HomeScreen(uiState: HomeUiState, navController: NavHostController) {
    NetworkAwareContent(
        onlineContent = { HomeContent(uiState) },
        offlineContent = {
            OfflineContent(
                message = stringResource(R.string.you_re_offline),
                subMessage = stringResource(R.string.most_of_the_features_will_be_limited_reconnect_and_refresh_to_see_the_content)
            )
        }
    )
}

@Composable
private fun HomeContent(uiState: HomeUiState) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (uiState) {
            is HomeUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is HomeUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextBold(
                        text = "Error",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                    TextMedium(
                        text = uiState.message,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            is HomeUiState.Success -> {
                CompetitionsList(competitions = uiState.competitions)
            }
        }
    }
}

@Composable
private fun CompetitionsList(competitions: List<Competition>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            TextBold(
                text = "Competitions",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(competitions) { competition ->
            CompetitionCard(competition = competition)
        }
    }
}

@Composable
private fun CompetitionCard(competition: Competition) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Competition emblem
            if (!competition.emblem.isNullOrEmpty()) {
                AsyncImage(
                    model = competition.emblem,
                    contentDescription = competition.name,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                TextBold(
                    text = competition.name.orEmpty(),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!competition.area?.flag.isNullOrEmpty()) {
                        AsyncImage(
                            model = competition.area.flag,
                            contentDescription = competition.area.name,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    TextRegular(
                        text = competition.area?.name.orEmpty(),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                TextRegular(
                    text = when {
                        competition.currentSeason == null -> "No season data"
                        competition.currentSeason.currentMatchday != null -> "Matchday ${competition.currentSeason.currentMatchday}"
                        else -> "No active matchday"
                    },
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Competition code badge
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                TextMedium(
                    text = competition.code.orEmpty(),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}


package com.samkt.filmio.presentation.tvSeriesScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.samkt.filmio.R

@Composable
fun TvSeriesTopSection(
    modifier: Modifier = Modifier,
    category: String = "",
    onSearchClicked: () -> Unit,
    onTrendingClicked: () -> Unit,
    onPopularClicked: () -> Unit,
    onUpcomingClicked: () -> Unit,
    onFilterClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tv Series",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            IconButton(onClick = onSearchClicked) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search"
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(onClick = onTrendingClicked) {
                Text(
                    text = "Trending",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (category == "trending") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
            }
            TextButton(onClick = onPopularClicked) {
                Text(
                    text = "Popular",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (category == "popular") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
            }
            TextButton(onClick = onUpcomingClicked) {
                Text(
                    text = "Latest",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (category == "latest") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onFilterClicked) {
                    Text(text = "filter")
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Filter"
                    )
                }
            }
        }
    }
}

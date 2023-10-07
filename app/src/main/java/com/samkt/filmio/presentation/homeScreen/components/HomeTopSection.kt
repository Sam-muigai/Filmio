package com.samkt.filmio.presentation.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.samkt.filmio.R
import com.samkt.filmio.ui.theme.ruberoid

@Composable
fun HomeTopSection(
    modifier: Modifier = Modifier,
    onTrendingClicked: () -> Unit,
    onPopularClicked: () -> Unit,
    onSearchClicked:()->Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.films),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "FilmIo",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = ruberoid,
                    ),
                )
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onSearchClicked) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(id = R.drawable.ic_account),
                    contentDescription = "Account",
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 16.dp),
        ) {
            TextButton(onClick = onTrendingClicked) {
                Text(
                    text = "Trending",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            TextButton(onClick = onPopularClicked) {
                Text(
                    text = "Popular",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}
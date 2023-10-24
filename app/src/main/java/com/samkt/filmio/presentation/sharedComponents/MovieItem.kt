package com.samkt.filmio.presentation.sharedComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.samkt.filmio.data.dtos.Movie

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Row(
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val image = movie.posterPath ?: movie.backdropPath
        val movieImageUrl = "https://image.tmdb.org/t/p/w500/${image}"
        MovieCard(
            modifier = Modifier
                .width(70.dp)
                .height(90.dp),
            imageUrl = movieImageUrl,
            cornerSize = 4.dp,
            clickable = false
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = movie.title ?: movie.originalTitle ?: "Unknown",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = movie.overview ?: "Unknown",
                maxLines = 2,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Light
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
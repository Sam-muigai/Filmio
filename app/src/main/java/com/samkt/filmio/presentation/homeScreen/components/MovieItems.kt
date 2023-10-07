package com.samkt.filmio.presentation.homeScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.samkt.filmio.data.dtos.Result

@Composable
fun MovieItems(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Result>
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        content = {
            items(movies.itemCount,key = {it}) { movieIndex ->
                val movieUrl = movies[movieIndex]?.posterPath ?: ""
                val imageUrl = "https://image.tmdb.org/t/p/w500/$movieUrl"
                val title =
                    movies[movieIndex]?.title ?: movies[movieIndex]?.originalName ?: "No name"
                Column(
                    modifier = Modifier
                        .width(110.dp)
                        .height(180.dp)
                ) {
                    MovieCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(4.dp),
                        imageUrl = imageUrl,
                        cornerSize = 4.dp,
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = title,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
        },
    )
}
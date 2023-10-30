package com.samkt.filmio.presentation.sharedComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.samkt.filmio.data.dtos.Movie

@Composable
fun MoviesLazyGrid(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        content = {
            items(
                count = movies.itemCount,
                key = { index -> index }
            ) { movieIndex ->
                val movieUrl = movies[movieIndex]?.posterPath ?: ""
                val backDropPath = movies[movieIndex]?.backdropPath ?: ""
                val imageUrl = "https://image.tmdb.org/t/p/w500/$movieUrl"
                val backDropPathImage = "https://image.tmdb.org/t/p/w500/$backDropPath"
                MovieCard(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(180.dp),
                    imageUrl = imageUrl,
                    cornerSize = 4.dp,
                    onMovieClicked = {
                        onMovieClicked.invoke(
                            movies[movieIndex]?.id ?: 240,
                            backDropPathImage,
                            imageUrl
                        )
                    }
                )
            }
        }
    )
}

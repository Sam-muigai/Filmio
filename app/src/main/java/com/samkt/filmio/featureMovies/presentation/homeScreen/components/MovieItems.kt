package com.samkt.filmio.featureMovies.presentation.homeScreen.components

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
import com.samkt.filmio.featureMovies.data.remote.dtos.Movie
import com.samkt.filmio.featureMovies.data.remote.dtos.TVSeries
import com.samkt.filmio.featureMovies.presentation.sharedComponents.MovieCard

@Composable
fun MovieItems(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    tvSeries: LazyPagingItems<TVSeries>? = null,
    isMovies: Boolean = true,
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp),
        content = {
            if (isMovies) {
                items(movies.itemCount, key = { it }) { movieIndex ->
                    val movieUrl = movies[movieIndex]?.posterPath ?: ""
                    val movieId = movies[movieIndex]?.id ?: 0
                    val backDropPath = movies[movieIndex]?.backdropPath ?: ""
                    val imageUrl = "https://image.tmdb.org/t/p/w500/$movieUrl"
                    val title = movies[movieIndex]?.title ?: movies[movieIndex]?.originalTitle
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
                            onMovieClicked = {
                                onMovieClicked(movieId, backDropPath, movieUrl)
                            }
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = title!!,
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            } else {
                items(tvSeries?.itemCount!!, key = { it }) { tvIndex ->
                    val tvUrl = tvSeries[tvIndex]?.posterPath ?: ""
                    val tvBacKDropPath = tvSeries[tvIndex]?.posterPath ?: ""
                    val tvImageId = tvSeries[tvIndex]?.id ?: 0
                    val tvImageUrl = "https://image.tmdb.org/t/p/w500/$tvUrl"
                    val tvTitle = tvSeries[tvIndex]?.name ?: tvSeries[tvIndex]?.originalName
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
                            imageUrl = tvImageUrl,
                            cornerSize = 4.dp,
                            onMovieClicked = {
                                onMovieClicked(tvImageId, tvBacKDropPath, tvUrl)
                            }
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = tvTitle!!,
                            style = MaterialTheme.typography.bodySmall,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    )
}

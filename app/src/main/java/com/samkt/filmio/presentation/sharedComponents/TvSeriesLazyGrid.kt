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
import com.samkt.filmio.data.remote.dtos.TVSeries

@Composable
fun TvSeriesLazyGrid(
    modifier: Modifier = Modifier,
    tvSeries: LazyPagingItems<TVSeries>,
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
        content = {
            items(
                count = tvSeries.itemCount,
                key = { index -> index }
            ) { movieIndex ->
                val series = tvSeries[movieIndex]
                val tvUrl = tvSeries[movieIndex]?.posterPath ?: ""
                val imageUrl = "https://image.tmdb.org/t/p/w500/$tvUrl"
                // For some reason some films are movies hence the need to filter them out
                val tvSeriesName = tvSeries[movieIndex]?.name ?: tvSeries[movieIndex]?.originalName
                tvSeriesName?.let {
                    MovieCard(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .height(180.dp),
                        imageUrl = imageUrl,
                        cornerSize = 4.dp,
                        onMovieClicked = {
                            onTvSeriesClicked(
                                series?.id ?: 88236,
                                series?.backdropPath ?: "",
                                series?.posterPath ?: ""
                            )
                        },
                        clickable = true
                    )
                }
            }
        }
    )
}

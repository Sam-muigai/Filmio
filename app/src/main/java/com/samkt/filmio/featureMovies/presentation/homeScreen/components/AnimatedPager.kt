package com.samkt.filmio.featureMovies.presentation.homeScreen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.samkt.filmio.featureMovies.data.remote.dtos.Movie
import com.samkt.filmio.featureMovies.presentation.homeScreen.components.pager.pagerAnimation
import com.samkt.filmio.featureMovies.presentation.sharedComponents.MovieCard
import com.samkt.filmio.util.toMovieGenre

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedViewPager(
    modifier: Modifier = Modifier,
    pageSize: Dp,
    movies: LazyPagingItems<Movie>,
    onDetailsClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit = { _, _, _ -> }
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {
            movies.itemCount
        }
    )

    val currentPageIndex = rememberSaveable { mutableIntStateOf(0) }
    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(
        key1 = true,
        block = {
            snapshotFlow { pagerState.currentPage }.collect { currentPage ->
                if (currentPageIndex.intValue == currentPage) {
                    hapticFeedback.performHapticFeedback(
                        hapticFeedbackType = HapticFeedbackType.LongPress
                    )
                    currentPageIndex.intValue = currentPage
                }
            }
        }
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isMoviesLoading = movies.loadState.refresh is LoadState.Loading
        val genre = if (!isMoviesLoading) {
            movies[pagerState.currentPage]?.genreIds
                ?: emptyList()
        } else {
            emptyList()
        }
        val genresCount = genre.size
        val currentMovie = if (!isMoviesLoading) movies[pagerState.currentPage] else null
        // Still open to future improvements..
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp),
            state = pagerState,
            contentPadding = PaddingValues(horizontal = pageSize - 40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) { thisPageIndex ->
            val image = movies[thisPageIndex]?.posterPath ?: ""
            val imageUrl = "https://image.tmdb.org/t/p/w500/$image"
            MovieCard(
                modifier = Modifier
                    .size(width = pageSize + 260.dp, height = 320.dp)
                    .pagerAnimation(
                        pagerState = pagerState,
                        thisPageIndex = thisPageIndex
                    ),
                imageUrl = imageUrl,
                clickable = true,
                onMovieClicked = {
                    onDetailsClicked(
                        currentMovie?.id ?: 0,
                        currentMovie?.backdropPath ?: "",
                        currentMovie?.posterPath ?: ""
                    )
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (!isMoviesLoading) {
            Text(
                text = movies[pagerState.currentPage]?.title ?: "No name",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = buildAnnotatedString {
                    append("Movie")
                    if (genre.isNotEmpty()) {
                        append(
                            ": "
                        )
                        genre.forEachIndexed { index, genreId ->
                            append(
                                genreId.toMovieGenre()
                            )
                            if (index != genresCount - 1) {
                                append(
                                    ", "
                                )
                            }
                        }
                    }
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

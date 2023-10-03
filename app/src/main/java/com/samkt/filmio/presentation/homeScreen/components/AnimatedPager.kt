package com.samkt.filmio.presentation.homeScreen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.samkt.filmio.data.dtos.Result
import com.samkt.filmio.presentation.homeScreen.components.pager.pagerAnimation
import com.samkt.filmio.util.toGenre

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedViewPager(
    modifier: Modifier = Modifier,
    pageSize: Dp,
    movies: LazyPagingItems<Result>,
    onAddToList: (Result) -> Unit = {},
    onDetailsClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit = { _, _, _ -> },
) {
    val pagerState = rememberPagerState(
        initialPageOffsetFraction = 0f,
        initialPage = 0,
    )

    val currentPageIndex = remember { mutableStateOf(0) }
    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(
        key1 = true,
        block = {
            snapshotFlow { pagerState.currentPage }.collect { currentPage ->
                if (currentPageIndex.value == currentPage) {
                    hapticFeedback.performHapticFeedback(
                        hapticFeedbackType = HapticFeedbackType.LongPress,
                    )
                    currentPageIndex.value = currentPage
                }
            }
        },
    )
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
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
            verticalAlignment = Alignment.CenterVertically,
            pageCount = movies.itemCount,
        ) { thisPageIndex ->
            val image = movies[thisPageIndex]?.posterPath ?: ""
            val imageUrl = "https://image.tmdb.org/t/p/w500/$image"
            MovieCard(
                modifier = Modifier
                    .size(width = pageSize + 260.dp, height = 320.dp)
                    .pagerAnimation(
                        pagerState = pagerState,
                        thisPageIndex = thisPageIndex,
                    ),
                imageUrl = imageUrl,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (!isMoviesLoading) {
            Text(
                text = movies[pagerState.currentPage]?.title ?: "No name",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = buildAnnotatedString {
                    append("Movie")
                    if (genre.isNotEmpty()) {
                        append(
                            ": ",
                        )
                        genre.forEachIndexed { index, genreId ->
                            append(
                                genreId.toGenre(),
                            )
                            if (index != genresCount - 1) {
                                append(
                                    ", ",
                                )
                            }
                        }
                    }
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (!isMoviesLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            onDetailsClicked(
                                currentMovie?.id ?: 0,
                                currentMovie?.backdropPath ?: "",
                                currentMovie?.posterPath ?: "",
                            )
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Movie Details",
                    )
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }
                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        movies[pagerState.currentPage]?.let { onAddToList(it) }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Text(
                        text = "ADD TO LIST",
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

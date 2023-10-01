package com.samkt.filmio.presentation.homeScreen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.paging.compose.LazyPagingItems
import com.samkt.filmio.data.dtos.Result
import com.samkt.filmio.presentation.homeScreen.components.pager.pagerAnimation
import com.samkt.filmio.ui.theme.GreenYellow
import com.samkt.filmio.util.toGenre

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedViewPager(
    modifier: Modifier = Modifier,
    pageSize: Dp,
    movies: LazyPagingItems<Result>,
    onAddToList: (Result) -> Unit = {},
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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val isMoviesEmpty = movies.itemCount == 0
        val genre = if (!isMoviesEmpty) {
            movies[pagerState.currentPage]?.genreIds
                ?: emptyList()
        } else {
            emptyList()
        }
        val genresCount = genre.size
        // Still open to future improvements..
        HorizontalPager(
            modifier = modifier.fillMaxWidth().height(320.dp),
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
        if (!isMoviesEmpty) {
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
        if (!isMoviesEmpty) {
            Button(
                onClick = {
                    movies[pagerState.currentPage]?.let { onAddToList(it) }
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenYellow,
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

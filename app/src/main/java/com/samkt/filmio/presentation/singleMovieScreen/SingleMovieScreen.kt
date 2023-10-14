package com.samkt.filmio.presentation.singleMovieScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.samkt.filmio.presentation.homeScreen.components.pager.pagerAnimation
import com.samkt.filmio.presentation.sharedComponents.MovieCard
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun SingleMovieScreen(
    movieImage: String,
    backGroundImage: String,
    movieId: Int,
    viewModel: SingleMovieViewModel = hiltViewModel()
) {
    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.getMovieDetails(movieId)
        }
    )
    val movieUiState = viewModel.movieScreenUiState.collectAsState().value
    SingleMovieScreenContent(
        movieImage = movieImage,
        backGroundImage = backGroundImage,
        uiState = movieUiState,
        movieId = movieId
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SingleMovieScreenContent(
    modifier: Modifier = Modifier,
    movieImage: String,
    movieId: Int,
    backGroundImage: String,
    uiState: MovieScreenUiState
) {
    val state = rememberCollapsingToolbarScaffoldState()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {
            tabs.size
        }
    )
    val scope = rememberCoroutineScope()
    val movieDetails = uiState.movieDetails
    CollapsingToolbarScaffold(
        modifier = modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val posterImageUrl = "https://image.tmdb.org/t/p/w500/$movieImage"
            val backgroundImageUrl = "https://image.tmdb.org/t/p/w500/$backGroundImage"
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .height(50.dp)
                    .pin()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .graphicsLayer {
                        // change alpha of Image as the toolbar expands
                        alpha = if (state.toolbarState.progress == 0f) 0f else 1f
                    }
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    MovieCard(
                        modifier = Modifier.fillMaxSize(),
                        imageUrl = backgroundImageUrl
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        MaterialTheme.colorScheme.background
                                    ),
                                    startY = 150f
                                )
                            )
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .height(150.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    MovieCard(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(110.dp),
                        imageUrl = posterImageUrl,
                        cornerSize = 8.dp
                    )
                    Text(
                        text = movieDetails?.originalTitle ?: "No name",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            content = {
                stickyHeader {
                   Tabs(tabs = tabs)
                }
                items(50) { index ->
                    Text(text = index.toString())
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(
    tabs:List<String>
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {
            tabs.size
        }
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = {

        }
    ) {
        tabs.forEachIndexed { index, tabName ->
            Tab(
                text = {
                    Text(text = tabName)
                },
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }

}

val tabs = listOf("Overview", "Cast", "Related")
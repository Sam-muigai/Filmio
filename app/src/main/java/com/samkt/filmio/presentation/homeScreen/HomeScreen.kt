package com.samkt.filmio.presentation.homeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.samkt.filmio.data.dtos.Result
import com.samkt.filmio.data.dtos.TVSeries
import com.samkt.filmio.presentation.homeScreen.components.AnimatedViewPager
import com.samkt.filmio.presentation.homeScreen.components.HomeTopSection
import com.samkt.filmio.presentation.homeScreen.components.MovieItems

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()
    val trendingTvSeries = viewModel.trendingTvSeries.collectAsLazyPagingItems()
    val upComingMovies = viewModel.upcomingMovies.collectAsLazyPagingItems()
    val topRatedMovies = viewModel.topRatedMovies.collectAsLazyPagingItems()

    val isTrendingTvSeriesLoading = trendingTvSeries.loadState.refresh is LoadState.Loading
    val isPopularMoviesLoading = popularMovies.loadState.refresh is LoadState.Loading
    val isTrendingMoviesLoading = trendingMovies.loadState.refresh is LoadState.Loading
    val isUpcomingMoviesLoading = upComingMovies.loadState.refresh is LoadState.Loading
    val isTopRatedMoviesLoading = topRatedMovies.loadState.refresh is LoadState.Loading

    val trendingTvSeriesError = trendingTvSeries.loadState.refresh is LoadState.Error
    val popularMoviesError = popularMovies.loadState.refresh is LoadState.Error
    val trendingMoviesError = trendingMovies.loadState.refresh is LoadState.Error
    val upcomingMoviesError = upComingMovies.loadState.refresh is LoadState.Error
    val topRatedMoviesError = topRatedMovies.loadState.refresh is LoadState.Error

    val isLoading =
        isPopularMoviesLoading || isTrendingMoviesLoading ||
                isTrendingTvSeriesLoading || isUpcomingMoviesLoading || isTopRatedMoviesLoading

    val hasErrors =
        trendingTvSeriesError || popularMoviesError || trendingMoviesError || upcomingMoviesError || topRatedMoviesError

    var showPopularMovies by rememberSaveable {
        mutableStateOf(true)
    }
    HomeScreenContent(
        isLoading = isLoading,
        hasErrors = hasErrors,
        popularMovies = popularMovies,
        onTrendingClicked = {},
        onPopularClicked = {},
        onRetryClicked = viewModel::getMovies,
        trendingMovies = trendingMovies,
        showPopularMovies = {
            showPopularMovies = it
        },
        isPopularMovies = showPopularMovies,
        popularTvSeries = trendingTvSeries,
        upComingMovies = upComingMovies,
        topRatedMovies = topRatedMovies,
        onSearchClicked = {
            // TODO: Navigate to search screen
        },
        onMovieClicked = onMovieClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    hasErrors: Boolean = false,
    popularMovies: LazyPagingItems<Result>,
    trendingMovies: LazyPagingItems<Result>,
    popularTvSeries: LazyPagingItems<TVSeries>,
    upComingMovies: LazyPagingItems<Result>,
    topRatedMovies: LazyPagingItems<Result>,
    onRetryClicked: () -> Unit,
    onTrendingClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    showPopularMovies: (Boolean) -> Unit,
    isPopularMovies: Boolean,
    onPopularClicked: () -> Unit,
    onViewAllClicked: (category: String) -> Unit = {},
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val pageWidth = (screenWidth / 3f).dp
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            HomeTopSection(
                onTrendingClicked = onTrendingClicked,
                onPopularClicked = onPopularClicked,
                onSearchClicked = onSearchClicked
            )
        },
        bottomBar = {
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
    ) { paddingValues ->
        AnimatedVisibility(
            visible = hasErrors,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                TextButton(onClick = onRetryClicked) {
                    Text(text = "An error occurred..Tap to try again")
                }
            }
        }

        AnimatedVisibility(
            visible = !isLoading && !hasErrors,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LazyColumn(
                contentPadding = paddingValues,
                content = {
                    item {
                        AnimatedViewPager(
                            modifier = Modifier.height(450.dp),
                            pageSize = pageWidth,
                            movies = popularMovies,
                            onDetailsClicked = onMovieClicked
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Trending",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            Text(
                                modifier = Modifier
                                    .clickable {
                                        onViewAllClicked("trending")
                                    }
                                    .padding(8.dp),
                                text = "View all",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            )
                        }
                    }
                    item {
                        MovieItems(
                            movies = trendingMovies,
                            onMovieClicked = onMovieClicked
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Popular",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                            )
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                Text(
                                    modifier = Modifier
                                        .clickable {
                                            showPopularMovies(true)
                                        }
                                        .padding(8.dp),
                                    text = "Movies",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = if (isPopularMovies) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                    ),
                                )
                                Text(
                                    modifier = Modifier
                                        .clickable {
                                            showPopularMovies(false)
                                        }
                                        .padding(8.dp),
                                    text = "TV Series",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = if (!isPopularMovies) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                                    ),
                                )

                            }
                        }
                    }
                    item {
                        MovieItems(
                            movies = popularMovies,
                            tvSeries = popularTvSeries,
                            isMovies = isPopularMovies,
                            onMovieClicked = onMovieClicked
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Upcoming",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                            )

                            Text(
                                modifier = Modifier
                                    .clickable {
                                        onViewAllClicked("upComing")
                                    }
                                    .padding(8.dp),
                                text = "View all",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            )


                        }
                    }
                    item {
                        MovieItems(
                            movies = upComingMovies,
                            onMovieClicked = onMovieClicked
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Top Rated",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                            )

                            Text(
                                modifier = Modifier
                                    .clickable {
                                        onViewAllClicked("topRated")
                                    }
                                    .padding(8.dp),
                                text = "View all",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            )
                        }

                    }
                    item {
                        MovieItems(
                            movies = topRatedMovies,
                            onMovieClicked = onMovieClicked
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                },
            )
        }
    }
}




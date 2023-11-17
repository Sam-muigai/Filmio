package com.samkt.filmio.featureMovies.presentation.homeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.samkt.filmio.featureMovies.data.remote.dtos.Movie
import com.samkt.filmio.featureMovies.data.remote.dtos.TVSeries
import com.samkt.filmio.featureMovies.presentation.homeScreen.components.AnimatedViewPager
import com.samkt.filmio.featureMovies.presentation.homeScreen.components.HomeTopSection
import com.samkt.filmio.featureMovies.presentation.homeScreen.components.MovieItems
import com.samkt.filmio.featureMovies.presentation.sharedComponents.ErrorAnimation

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onSearchClicked: () -> Unit,
    onViewAllClicked: (category: String) -> Unit,
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit,
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    val context = LocalContext.current

    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()
    val trendingTvSeries = viewModel.popularTvSeries.collectAsLazyPagingItems()
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
    val isSaved = viewModel.existInDb.value != 0
    HomeScreenContent(
        isLoading = isLoading,
        hasErrors = hasErrors,
        popularMovies = popularMovies,
        onRetryClicked = viewModel::getMovies,
        trendingMovies = trendingMovies,
        showPopularMovies = {
            showPopularMovies = it
        },
        isPopularMovies = showPopularMovies,
        popularTvSeries = trendingTvSeries,
        upComingMovies = upComingMovies,
        topRatedMovies = topRatedMovies,
        onSearchClicked = onSearchClicked,
        onMovieClicked = onMovieClicked,
        onTvSeriesClicked = onTvSeriesClicked,
        onViewAllClicked = onViewAllClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    hasErrors: Boolean = false,
    popularMovies: LazyPagingItems<Movie>,
    trendingMovies: LazyPagingItems<Movie>,
    popularTvSeries: LazyPagingItems<TVSeries>,
    upComingMovies: LazyPagingItems<Movie>,
    topRatedMovies: LazyPagingItems<Movie>,
    onRetryClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    showPopularMovies: (Boolean) -> Unit,
    isPopularMovies: Boolean,
    onViewAllClicked: (category: String) -> Unit = {},
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit,
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val pageWidth = (screenWidth / 3f).dp
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            HomeTopSection(
                onTrendingClicked = {
                    onViewAllClicked("Trending")
                },
                onPopularClicked = {
                    onViewAllClicked("Popular")
                },
                onSearchClicked = onSearchClicked
            )
        },
        bottomBar = {
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
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
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onRetryClicked.invoke() }
                ) {
                    ErrorAnimation(
                        modifier = Modifier.size(200.dp)
                    )
                    Text(
                        text = "Error occurred. Click to retry",
                        style = MaterialTheme.typography.titleMedium
                    )
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
                            modifier = Modifier,
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
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Trending",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                modifier = Modifier
                                    .clickable {
                                        onViewAllClicked("Trending")
                                    }
                                    .padding(8.dp),
                                text = "View all",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
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
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Popular",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
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
                                    )
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
                                    )
                                )
                            }
                        }
                    }
                    item {
                        MovieItems(
                            movies = popularMovies,
                            tvSeries = popularTvSeries,
                            isMovies = isPopularMovies,
                            onMovieClicked = if (isPopularMovies) onMovieClicked else onTvSeriesClicked
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Upcoming",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Text(
                                modifier = Modifier
                                    .clickable {
                                        onViewAllClicked("Upcoming")
                                    }
                                    .padding(8.dp),
                                text = "View all",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
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
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Top Rated",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Text(
                                modifier = Modifier
                                    .clickable {
                                        onViewAllClicked("TopRated")
                                    }
                                    .padding(8.dp),
                                text = "View all",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
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
                }
            )
        }
    }
}

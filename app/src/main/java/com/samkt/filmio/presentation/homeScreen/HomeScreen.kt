package com.samkt.filmio.presentation.homeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.samkt.filmio.R
import com.samkt.filmio.data.dtos.Result
import com.samkt.filmio.presentation.homeScreen.components.AnimatedViewPager
import com.samkt.filmio.presentation.homeScreen.components.MovieCard

import com.samkt.filmio.ui.theme.ruberoid
import com.samkt.filmio.util.toErrorMessage
import timber.log.Timber

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()
    val isPopularMoviesLoading = popularMovies.loadState.refresh is LoadState.Loading
    val isTrendingMoviesLoading = trendingMovies.loadState.refresh is LoadState.Loading
    val showLoadingBar = isPopularMoviesLoading || isTrendingMoviesLoading
    HomeScreenContent(
        isLoading = showLoadingBar,
        popularMovies = popularMovies,
        onTrendingClicked = {},
        onPopularClicked = {},
        trendingMovies = trendingMovies,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    popularMovies: LazyPagingItems<Result>,
    trendingMovies: LazyPagingItems<Result>,
    onTrendingClicked: () -> Unit,
    onPopularClicked: () -> Unit,
    onViewAllClicked: (category: String) -> Unit = {}
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val pageWidth = (screenWidth / 3f).dp
    val isTrendingMoviesEmpty = trendingMovies.itemCount == 0
    Timber.d(pageWidth.toString())
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopSection(
                onTrendingClicked = onTrendingClicked,
                onPopularClicked = onPopularClicked,
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
            visible = !isLoading,
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
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Trending",
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Text(
                                modifier = Modifier.clickable {
                                    onViewAllClicked("trending")
                                },
                                text = "View all",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                            )
                        }
                    }
                    item {
                        MovieItems(movies = trendingMovies)
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier,
                                text = "Popular",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                ),
                            )
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    modifier = Modifier.clickable {},
                                    text = "Movies",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                )
                                Text(
                                    modifier = Modifier.clickable {},
                                    text = "TV Series",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onBackground
                                    ),
                                )
                            }
                        }
                    }
                    item {
                        MovieItems(movies = popularMovies)
                    }
                },
            )
        }
    }
}

@Composable
fun MovieItems(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Result>
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        content = {
            items(movies.itemCount) { movieIndex ->
                val movieUrl = movies[movieIndex]?.posterPath ?: ""
                val imageUrl = "https://image.tmdb.org/t/p/w500/$movieUrl"
                MovieCard(
                    modifier = Modifier
                        .width(110.dp)
                        .height(150.dp)
                        .padding(4.dp),
                    imageUrl = imageUrl,
                    cornerSize = 8.dp,
                )
            }
        },
    )
}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    onTrendingClicked: () -> Unit,
    onPopularClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = R.drawable.films),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "FilmIo",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = ruberoid,
                    ),
                )
            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(id = R.drawable.ic_account),
                    contentDescription = "Account",
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 16.dp),
        ) {
            TextButton(onClick = onTrendingClicked) {
                Text(
                    text = "Trending",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            TextButton(onClick = onPopularClicked) {
                Text(
                    text = "Popular",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

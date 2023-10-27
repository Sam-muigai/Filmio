package com.samkt.filmio.presentation.categoryScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.samkt.filmio.R
import com.samkt.filmio.data.dtos.Movie
import com.samkt.filmio.presentation.sharedComponents.MoviesLazyGrid

@Composable
fun CategoryScreen(
    category: String?,
    viewModel: CategoryScreenViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit,
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()

    val isLoading = movies.loadState.refresh is LoadState.Loading
    val isError = movies.loadState.refresh is LoadState.Error

    CategoryScreenContent(
        movies = movies,
        isLoading = isLoading,
        isError = isError,
        category = category ?: "Popular",
        onMovieClicked = onMovieClicked,
        onBackClicked = onBackClicked,
        isMovie = viewModel.isMovie,
        setIsMovie = viewModel::setIsMovie
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreenContent(
    movies: LazyPagingItems<Movie>,
    isLoading: Boolean,
    category: String,
    isError: Boolean,
    onBackClicked:()->Unit,
    setIsMovie:(Boolean) ->Unit,
    isMovie:Boolean,
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 12.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search"
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .clickable {
                               setIsMovie(true)
                            }
                            .padding(8.dp),
                        text = "Movies",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color =  if (isMovie) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        ),
                    )
                    Text(
                        modifier = Modifier
                            .clickable {
                                setIsMovie(false)
                            }
                            .padding(8.dp),
                        text = "TvSeries",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = if (!isMovie) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        ),
                    )
                }
            }
        }
    ) { paddingValues ->
        AnimatedVisibility(
            visible = !isLoading && !isError,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                MoviesLazyGrid(
                    movies = movies,
                    onMovieClicked = onMovieClicked
                )
            }
        }
    }
}
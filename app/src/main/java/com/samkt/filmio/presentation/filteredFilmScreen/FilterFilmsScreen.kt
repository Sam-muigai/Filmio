package com.samkt.filmio.presentation.filteredFilmScreen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.samkt.filmio.R
import com.samkt.filmio.data.dtos.Movie
import com.samkt.filmio.data.dtos.TVSeries
import com.samkt.filmio.presentation.sharedComponents.MoviesLazyGrid
import com.samkt.filmio.presentation.sharedComponents.TvSeriesLazyGrid
import dagger.Lazy

@Composable
fun FilterFilmScreen(
    viewModel: FilterFilmsScreenViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit = { _, _, _ -> },
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit = { _, _, _ -> },
) {
    if (viewModel.type == "Movies"){
        val movies = viewModel.movies.collectAsLazyPagingItems()
        FilterFilmScreenContent(
            onBackClicked = onBackClicked,
            type = viewModel.type,
            category = viewModel.category,
            genre = viewModel.genre,
            movies = movies,
            onMovieClicked = onMovieClicked
        )
    }else{
        val tvSeries = viewModel.tvSeries.collectAsLazyPagingItems()
        FilterFilmScreenContent(
            onBackClicked = onBackClicked,
            type = viewModel.type,
            category = viewModel.category,
            genre = viewModel.genre,
            tvSeries = tvSeries,
            onTvSeriesClicked = onTvSeriesClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterFilmScreenContent(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    type: String,
    category: String,
    genre: String,
    movies: LazyPagingItems<Movie>? = null,
    tvSeries: LazyPagingItems<TVSeries>? = null,
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit = { _, _, _ -> },
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit = { _, _, _ -> },
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
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
                        text = "Filter",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Chips(type = type, category = category, genre = genre)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (movies != null) {
                MoviesLazyGrid(
                    movies = movies,
                    onMovieClicked = onMovieClicked
                )
            }
            if (tvSeries != null) {
                TvSeriesLazyGrid(
                    tvSeries = tvSeries,
                    onTvSeriesClicked = onTvSeriesClicked
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chips(
    modifier: Modifier = Modifier,
    type: String,
    category: String,
    genre: String
) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = false,
            onClick = {},
            label = { Text(text = type) }
        )
        FilterChip(
            selected = false,
            onClick = {},
            label = { Text(text = category) }
        )
        FilterChip(
            selected = false,
            onClick = {},
            label = { Text(text = genre) }
        )
    }
}
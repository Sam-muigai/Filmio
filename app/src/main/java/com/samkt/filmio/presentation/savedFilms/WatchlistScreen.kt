package com.samkt.filmio.presentation.savedFilms

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.samkt.filmio.data.local.entities.MovieEntity
import com.samkt.filmio.data.local.entities.TvSeriesEntity
import com.samkt.filmio.presentation.sharedComponents.MovieItem

@Composable
fun WatchListScreen(
    viewModel: SavedFilmsViewModel = hiltViewModel()
) {
    val movies = viewModel.savedMovies.collectAsState().value
    val tvSeries = viewModel.savedTvSeries.collectAsState().value
    WatchListScreenContent(
        movies = movies,
        tvSeries = tvSeries
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun WatchListScreenContent(
    modifier: Modifier = Modifier,
    movies: List<MovieEntity>,
    tvSeries:List<TvSeriesEntity>
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "WatchList",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn(
                content = {
                    stickyHeader {
                        Text(text = "Movies")
                    }
                    items(movies){movie ->
                        MovieItem(
                            filmName = movie.title ?: movie.originalTitle ?: "",
                            filmOverview = movie.overview ?: "",
                            imageUrl = movie.posterPath ?: ""
                        )
                    }
                    stickyHeader {
                        Text(text = "Tv Series")
                    }
                    items(tvSeries){tvSeries ->
                        MovieItem(
                            filmName = tvSeries.name ?: tvSeries.originalName ?: "",
                            filmOverview = tvSeries.overview ?: "",
                            imageUrl = tvSeries.posterPath ?: ""
                        )
                    }
                }
            )
        }
    }
}
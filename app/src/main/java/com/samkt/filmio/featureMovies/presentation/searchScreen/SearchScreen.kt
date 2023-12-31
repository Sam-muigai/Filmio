package com.samkt.filmio.featureMovies.presentation.searchScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.samkt.filmio.featureMovies.presentation.sharedComponents.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit,
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit
) {
    val uiState = viewModel.searchScreenUiState.collectAsState().value
    var searchTerm by remember {
        mutableStateOf("")
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                value = searchTerm,
                onValueChange = {
                    searchTerm = it
                    viewModel.searchMovie(searchTerm)
                }
            )
            LazyColumn(
                contentPadding = paddingValues,
                content = {
                    items(items = uiState.movies, key = { it.id }) { film ->
                        val movieName = film.title ?: film.originalTitle
                        val tvSeriesName = film.name ?: film.originalName
                        val filmName = tvSeriesName ?: movieName
                        val imageUrl = film.backdropPath ?: film.posterPath
                        if (filmName != null && imageUrl != null) {
                            MovieItem(
                                imageUrl = imageUrl,
                                filmName = filmName,
                                filmOverview = film.overview,
                                clickable = true,
                                onClick = {
                                    // TODO: Replace empty strings with default images
                                    if (movieName != null) {
                                        onMovieClicked(
                                            film.id,
                                            film.backdropPath ?: "",
                                            film.posterPath ?: ""
                                        )
                                    } else {
                                        onTvSeriesClicked(
                                            film.id,
                                            film.backdropPath ?: "",
                                            film.posterPath ?: ""
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}

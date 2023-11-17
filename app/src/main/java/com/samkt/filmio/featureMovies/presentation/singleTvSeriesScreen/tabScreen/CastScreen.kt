package com.samkt.filmio.featureMovies.presentation.singleTvSeriesScreen.tabScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samkt.filmio.featureMovies.presentation.singleMovieScreen.components.CastItem
import com.samkt.filmio.featureMovies.presentation.singleTvSeriesScreen.TvSeriesUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvSeriesCastScreen(
    uiState: TvSeriesUiState
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.heightIn(min = 0.dp, max = 1000.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(uiState.cast) { cast ->
                        CastItem(
                            modifier = Modifier,
                            cast = cast
                        )
                    }
                }
            )
        }
    }
}

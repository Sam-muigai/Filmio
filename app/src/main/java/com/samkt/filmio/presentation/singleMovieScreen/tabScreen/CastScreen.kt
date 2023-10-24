package com.samkt.filmio.presentation.singleMovieScreen.tabScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.samkt.filmio.data.dtos.credits.Cast
import com.samkt.filmio.presentation.singleMovieScreen.MovieScreenUiState
import com.samkt.filmio.presentation.singleMovieScreen.components.CastItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastScreen(
    uiState: MovieScreenUiState
) {
    Scaffold {paddingValues ->
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


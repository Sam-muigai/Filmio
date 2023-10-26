package com.samkt.filmio.presentation.singleMovieScreen.tabScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.samkt.filmio.presentation.singleMovieScreen.MovieScreenUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieOverviewScreen(
    uiState: MovieScreenUiState,
) {
    val movieDetails = uiState.movieDetails

    Column(
        modifier = Modifier
            .padding(8.dp)
            .heightIn(max = 1000.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = movieDetails?.overview ?: "",
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Genre",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Text(
            text = buildAnnotatedString {
                movieDetails?.genres?.forEachIndexed { index, genre ->
                    append("${genre.name} ")
                    if (index != movieDetails.genres.size - 1) {
                        append(",")
                    }
                }
            },
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Production",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
        Text(
            text = buildAnnotatedString {
                movieDetails?.productionCompanies?.forEachIndexed { index, productionCompany ->
                    append(productionCompany.name)
                    if (index != movieDetails.productionCompanies.size - 1) {
                        append(" ,")
                    }
                }
            },
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }

    uiState.overViewError?.let { errorMessage ->
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = errorMessage)
        }
    }
}
package com.samkt.filmio.presentation.filterScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FilterScreen(
    viewModel: FilterScreenViewModel = hiltViewModel(),
    onBackClicked: () -> Unit = {},
    onSubmit: (type: String, category: String, genre: String) -> Unit
) {
    val isMovie = viewModel.selectedType == "Movies"
    FilterScreenContent(
        onBackClicked = onBackClicked,
        selectedType = viewModel.selectedType,
        selectedGenre = if (isMovie) viewModel.selectedMovieGenres else viewModel.selectedTvSeriesGenre,
        onSelectType = viewModel::onSelectType,
        onSelectGenre = if (isMovie) viewModel::onSelectMovieGenres else viewModel::onSelectedTvSeries,
        genres = if (isMovie) movieGenres else tvSeriesGenres,
        types = types,
        categories = categories,
        onSelectCategory = viewModel::onCategorySelect,
        selectedCategory = viewModel.selectedCategory,
        onSubmit = onSubmit
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterScreenContent(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    types: List<String> = emptyList(),
    categories: List<String> = emptyList(),
    genres: List<String> = emptyList(),
    selectedType: String,
    selectedCategory: String,
    selectedGenre: String,
    onSelectType: (String) -> Unit,
    onSelectCategory: (String) -> Unit,
    onSelectGenre: (String) -> Unit,
    onSubmit: (type: String, category: String, genre: String) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
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
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    onSubmit(selectedType, selectedCategory, selectedGenre)
                }
            ) {
                Text(text = "SUBMIT")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "Types",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                types.forEach { type ->
                    CheckBoxItem(
                        selectedOption = selectedType,
                        text = type,
                        onOptionSelect = onSelectType
                    )
                }
            }
            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "Category",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            FlowRow(
                maxItemsInEachRow = 3,
                verticalArrangement = Arrangement.Center
            ) {
                categories.forEach { category ->
                    CheckBoxItem(
                        selectedOption = selectedCategory,
                        text = category,
                        onOptionSelect = onSelectCategory
                    )
                }
            }

            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = "Genre",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Normal
                )
            )
            FlowRow(
                maxItemsInEachRow = 3,
                verticalArrangement = Arrangement.Center
            ) {
                genres.forEach { genre ->
                    CheckBoxItem(
                        selectedOption = selectedGenre,
                        text = genre,
                        onOptionSelect = onSelectGenre
                    )
                }
            }
        }
    }
}

@Composable
fun CheckBoxItem(
    selectedOption: String,
    text: String,
    onOptionSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isChecked = selectedOption == text
        Checkbox(
            checked = selectedOption == text,
            onCheckedChange = {
                onOptionSelect(text)
            }
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal,
                color = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
            )
        )
    }
}

package com.samkt.filmio.featureMovies.presentation.tvSeriesScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.samkt.filmio.featureMovies.data.remote.dtos.TVSeries
import com.samkt.filmio.featureMovies.presentation.sharedComponents.TvSeriesLazyGrid
import com.samkt.filmio.featureMovies.presentation.tvSeriesScreen.components.TvSeriesTopSection

@Composable
fun TvSeriesScreen(
    viewModel: TvSeriesViewModel = hiltViewModel(),
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit,
    onSearchClicked: () -> Unit,
    onFilterClicked: () -> Unit
) {
    val trendingTvSeries = viewModel.trendingTvSeries.collectAsLazyPagingItems()
    val popularTvSeries = viewModel.popularTvSeries.collectAsLazyPagingItems()
    val latestTvSeries = viewModel.latestTvSeries.collectAsLazyPagingItems()

    val isTrendingTvSeriesLoading = trendingTvSeries.loadState.refresh is LoadState.Loading
    val isPopularTvSeriesLoading = popularTvSeries.loadState.refresh is LoadState.Loading
    val isLatestTvSeriesLoading = latestTvSeries.loadState.refresh is LoadState.Loading

    val isTrendingTvSeriesError = trendingTvSeries.loadState.refresh is LoadState.Error
    val isPopularTvSeriesError = popularTvSeries.loadState.refresh is LoadState.Error
    val isLatestTvSeriesError = latestTvSeries.loadState.refresh is LoadState.Error

    val isLoading = isTrendingTvSeriesLoading || isPopularTvSeriesLoading || isLatestTvSeriesLoading
    val isError = isPopularTvSeriesError || isTrendingTvSeriesError || isLatestTvSeriesError

    var category by rememberSaveable {
        mutableStateOf("trending")
    }
    val tvSeries = when (category) {
        "trending" -> trendingTvSeries
        "popular" -> popularTvSeries
        "latest" -> latestTvSeries
        else -> null
    }
    TvSeriesScreenContent(
        onSearchClicked = onSearchClicked,
        category = category,
        onCategoryClicked = {
            category = it
        },
        tvSeries = tvSeries!!,
        isLoading = isLoading,
        isError = isError,
        onTvSeriesClicked = onTvSeriesClicked,
        onFilterClicked = onFilterClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TvSeriesScreenContent(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
    category: String,
    isLoading: Boolean = false,
    isError: Boolean = false,
    onCategoryClicked: (String) -> Unit,
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit,
    tvSeries: LazyPagingItems<TVSeries>,
    onFilterClicked: () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TvSeriesTopSection(
                onSearchClicked = onSearchClicked,
                category = category,
                onTrendingClicked = { onCategoryClicked("trending") },
                onPopularClicked = { onCategoryClicked("popular") },
                onUpcomingClicked = { onCategoryClicked("latest") },
                onFilterClicked = onFilterClicked
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
        if (!isError && !isLoading) {
            TvSeriesLazyGrid(
                modifier = Modifier.padding(paddingValues),
                tvSeries = tvSeries,
                onTvSeriesClicked = onTvSeriesClicked
            )
        }
    }
}

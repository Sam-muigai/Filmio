package com.samkt.filmio.presentation.homeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.samkt.filmio.R
import com.samkt.filmio.data.dtos.Result
import com.samkt.filmio.presentation.homeScreen.components.AnimatedViewPager
import com.samkt.filmio.ui.theme.GreenYellow
import com.samkt.filmio.ui.theme.ruberoid
import timber.log.Timber
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val showLoadingBar = popularMovies.loadState.refresh is LoadState.Loading
    HomeScreenContent(
        showLoadingBar = showLoadingBar,
        popularMovies = popularMovies,
        onTrendingClicked = {},
        onPopularClicked = {},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    showLoadingBar: Boolean = false,
    popularMovies: LazyPagingItems<Result>,
    onTrendingClicked: () -> Unit,
    onPopularClicked: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val pageWidth = (screenWidth / 3f).dp
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
            if (showLoadingBar) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp),
                    color = GreenYellow,
                )
            }
        },
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues),
        ) {
            AnimatedViewPager(
                pageSize = pageWidth,
                movies = popularMovies,
            )
        }
    }
}


@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    onTrendingClicked: () -> Unit,
    onPopularClicked: () -> Unit,
) {
    Column(modifier = modifier.padding(8.dp)) {
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
                    color = Color.White,
                )
            }
            TextButton(onClick = onPopularClicked) {
                Text(
                    text = "Popular",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                )
            }
        }
    }
}

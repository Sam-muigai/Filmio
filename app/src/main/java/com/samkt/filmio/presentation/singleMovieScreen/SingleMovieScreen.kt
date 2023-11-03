package com.samkt.filmio.presentation.singleMovieScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.samkt.filmio.R
import com.samkt.filmio.data.remote.dtos.singleMovie.SingleMovieResponseDto
import com.samkt.filmio.presentation.sharedComponents.MovieCard
import com.samkt.filmio.presentation.sharedComponents.MovieTabsItem
import com.samkt.filmio.presentation.sharedComponents.movieTabs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun SingleMovieScreen(
    movieImage: String,
    backGroundImage: String,
    movieId: Int,
    onBackClicked: () -> Unit,
    viewModel: SingleMovieViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true, block = {
        viewModel.getMovieDetails(movieId)
    })
    val movieUiState = viewModel.movieScreenUiState.collectAsState().value
    SingleMovieScreenContent(
        movieImage = movieImage,
        backGroundImage = backGroundImage,
        uiState = movieUiState,
        onBackClicked = onBackClicked,
        isSaved = viewModel.existInDb.value != 0,
        onSaveMovieClicked = viewModel::saveMovie,
        onDeleteClicked = viewModel::delete
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleMovieScreenContent(
    modifier: Modifier = Modifier,
    movieImage: String,
    backGroundImage: String,
    uiState: MovieScreenUiState,
    onBackClicked: () -> Unit = {},
    isSaved: Boolean = false,
    onDeleteClicked: (SingleMovieResponseDto) -> Unit = {},
    onSaveMovieClicked: (SingleMovieResponseDto) -> Unit
) {
    val state = rememberCollapsingToolbarScaffoldState()
    val pagerState =
        rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f, pageCount = {
            3
        })
    val scope = rememberCoroutineScope()
    val movieDetails = uiState.movieDetails
    val dataRetrieved = !uiState.loading && uiState.overViewError == null
    CollapsingToolbarScaffold(modifier = modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val posterImageUrl = "https://image.tmdb.org/t/p/w500/$movieImage"
            val backgroundImageUrl = "https://image.tmdb.org/t/p/w500/$backGroundImage"
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .height(50.dp)
                    .pin()
            )
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .graphicsLayer {
                        alpha = if (state.toolbarState.progress == 0f) 0f else 1f
                    }) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        MovieCard(
                            modifier = Modifier.fillMaxSize(),
                            imageUrl = backgroundImageUrl,
                            clickable = false
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent, MaterialTheme.colorScheme.background
                                        ), startY = 150f
                                    )
                                )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp)
                            .height(150.dp), verticalAlignment = Alignment.Bottom
                    ) {
                        MovieCard(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(110.dp),
                            imageUrl = posterImageUrl,
                            cornerSize = 8.dp,
                            clickable = false
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        if (dataRetrieved) {
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = movieDetails?.originalTitle ?: "No name",
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(18.dp),
                                        painter = painterResource(id = R.drawable.ic_star),
                                        contentDescription = "Rating",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${movieDetails?.voteAverage ?: 0.0}",
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Normal
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "(${movieDetails?.voteCount ?: 0}) voted",
                                        color = MaterialTheme.colorScheme.onBackground,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Normal
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        onClick = {
                            if (isSaved)
                                uiState.movieDetails?.let { onDeleteClicked(it) }
                                else
                            uiState.movieDetails?.let { onSaveMovieClicked(it) }
                        },
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(text = if (isSaved) "DELETE" else "ADD TO LIST")
                    }
                    OutlinedButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "SHARE")
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .size(35.dp),
                    onClick = onBackClicked,
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.background,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                AnimatedVisibility(visible = state.toolbarState.progress == 0f) {
                    Text(
                        text = movieDetails?.title ?: "",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }) {
        LazyColumn(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
            content = {
                stickyHeader {
                    Column {
                        MovieTabs(pagerState = pagerState, scope = scope)
                        MovieTabContents(
                            modifier = Modifier,
                            pagerState = pagerState,
                            tabs = movieTabs,
                            overviewScreenState = uiState
                        )
                    }
                }
            })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieTabContents(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tabs: List<MovieTabsItem>,
    overviewScreenState: MovieScreenUiState
) {
    HorizontalPager(
        modifier = modifier.fillMaxSize(), state = pagerState
    ) { pageIndex ->
        tabs[pageIndex].screen(
            overviewScreenState
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieTabs(
    pagerState: PagerState, scope: CoroutineScope
) {
    TabRow(
        modifier = Modifier.fillMaxWidth(0.7f),
        divider = {},
        selectedTabIndex = pagerState.currentPage
    ) {
        movieTabs.forEachIndexed { index, tab ->
            Tab(selected = pagerState.currentPage == index, onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }, text = { Text(text = tab.label) })
        }
    }
}

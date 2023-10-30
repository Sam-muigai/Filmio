package com.samkt.filmio.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.samkt.filmio.presentation.categoryScreen.CategoryScreen
import com.samkt.filmio.presentation.filterScreen.FilterScreen
import com.samkt.filmio.presentation.filteredFilmScreen.FilterFilmScreen
import com.samkt.filmio.presentation.homeScreen.HomeScreen
import com.samkt.filmio.presentation.movieScreen.MoviesScreen
import com.samkt.filmio.presentation.searchScreen.SearchScreen
import com.samkt.filmio.presentation.singleMovieScreen.SingleMovieScreen
import com.samkt.filmio.presentation.singleTvSeriesScreen.TvSeriesDetailScreen
import com.samkt.filmio.presentation.tvSeriesScreen.TvSeriesScreen
import soup.compose.material.motion.animation.materialSharedAxisZIn
import soup.compose.material.motion.animation.materialSharedAxisZOut

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationHomeScreen(
    onMovieClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit,
    onTvSeriesClicked: (id: Int, backDropPath: String, posterImage: String) -> Unit,
    onViewAllClicked: (category: String) -> Unit,
    onSearchClicked: () -> Unit,
    onFilterClicked: () -> Unit
) {
    var navigationSelectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEachIndexed { index, navItems ->
                    val isSelected = navigationSelectedItem == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navItems.screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(25.dp),
                                painter = painterResource(
                                    id = if (isSelected) navItems.selectedIcon else navItems.unselectedIcon
                                ),
                                contentDescription = navItems.label
                            )
                        },
                        label = {
                            Text(text = navItems.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = Screens.HomeScreen.route
        ) {
            destination(Screens.HomeScreen.route) {
                HomeScreen(
                    onMovieClicked = onMovieClicked,
                    onSearchClicked = onSearchClicked,
                    onTvSeriesClicked = onTvSeriesClicked,
                    onViewAllClicked = onViewAllClicked
                )
            }
            destination(Screens.MovieScreen.route) {
                MoviesScreen(
                    onMovieClicked = onMovieClicked,
                    onSearchClicked = onSearchClicked,
                    onFilterClicked = onFilterClicked
                )
            }
            destination(Screens.TvSeriesScreen.route) {
                TvSeriesScreen(
                    onSearchClicked = onSearchClicked,
                    onTvSeriesClicked = onTvSeriesClicked,
                    onFilterClicked = onFilterClicked
                )
            }
            destination(Screens.WatchListScreen.route) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Watch List")
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.ApplicationHomePage.route) {
        destination(Screens.ApplicationHomePage.route) {
            ApplicationHomeScreen(
                onMovieClicked = { id, backDropPath, posterImage ->
                    navController.navigate(
                        Screens.SingleMovieScreen.route + "?backDropPath=$backDropPath?posterImage=$posterImage?movieId=$id"
                    )
                },
                onSearchClicked = {
                    navController.navigate(Screens.SearchScreen.route)
                },
                onTvSeriesClicked = { id, backDropPath, posterImage ->
                    navController.navigate(
                        Screens.SingleTvSeriesScreen.route + "?backDropPath=$backDropPath?posterImage=$posterImage?tvSeriesId=$id"
                    )
                },
                onViewAllClicked = { category ->
                    navController.navigate(Screens.CategoryScreen.route + "?category=$category")
                },
                onFilterClicked = {
                    navController.navigate(Screens.FilterScreen.route)
                }
            )
        }
        destination(
            route = Screens.SingleMovieScreen.route + "?backDropPath={backDropPath}" + "?posterImage={posterImage}" + "?movieId={movieId}",
            arguments = listOf(
                navArgument("backDropPath") {
                    type = NavType.StringType
                },
                navArgument("posterImage") {
                    type = NavType.StringType
                }
            )
        ) { navBackStack ->
            val posterImage = navBackStack.arguments?.getString("posterImage") ?: ""
            val backDropPath = navBackStack.arguments?.getString("backDropPath") ?: ""
            val movieId = navBackStack.arguments?.getString("movieId")
            SingleMovieScreen(
                movieImage = posterImage,
                backGroundImage = backDropPath,
                movieId = movieId!!.toInt(),
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        destination(
            route = Screens.SingleTvSeriesScreen.route + "?backDropPath={backDropPath}" + "?posterImage={posterImage}" + "?tvSeriesId={tvSeriesId}",
            arguments = listOf(
                navArgument("backDropPath") {
                    type = NavType.StringType
                },
                navArgument("posterImage") {
                    type = NavType.StringType
                }
            )
        ) { navBackStack ->
            val posterImage = navBackStack.arguments?.getString("posterImage") ?: ""
            val backDropPath = navBackStack.arguments?.getString("backDropPath") ?: ""
            val movieId = navBackStack.arguments?.getString("tvSeriesId")!!
            TvSeriesDetailScreen(
                tvSeriesId = movieId.toInt(),
                backDropPath = backDropPath,
                posterImage = posterImage,
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
        destination(Screens.SearchScreen.route) {
            SearchScreen(
                onMovieClicked = { id, backDropPath, posterImage ->
                    navController.navigate(
                        Screens.SingleMovieScreen.route + "?backDropPath=$backDropPath?posterImage=$posterImage?movieId=$id"
                    )
                },
                onTvSeriesClicked = { id, backDropPath, posterImage ->
                    navController.navigate(
                        Screens.SingleTvSeriesScreen.route + "?backDropPath=$backDropPath?posterImage=$posterImage?tvSeriesId=$id"
                    )
                }
            )
        }
        destination(
            route = Screens.CategoryScreen.route + "?category={category}",
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                }
            )
        ) { navBackStack ->
            val category = navBackStack.arguments?.getString("category")
            CategoryScreen(
                category = category,
                onMovieClicked = { id, backDropPath, posterImage ->
                    navController.navigate(
                        Screens.SingleMovieScreen.route + "?backDropPath=$backDropPath?posterImage=$posterImage?movieId=$id"
                    )
                },
                onBackClicked = {
                    navController.popBackStack()
                },
                onSearchClicked = {
                    navController.navigate(Screens.SearchScreen.route)
                }
            )
        }
        destination(
            route = Screens.FilterScreen.route
        ) {
            FilterScreen(
                onBackClicked = {
                    navController.popBackStack()
                },
                onSubmit = { type, category, genre ->
                    navController.navigate(
                        Screens.FilteredFilmsScreen.route + "?type=$type?category=$category?genre=$genre"
                    )
                }
            )
        }
        destination(
            route = Screens.FilteredFilmsScreen.route + "?type={type}" + "?category={category}" + "?genre={genre}",
            arguments = listOf(
                navArgument("type") {
                    type = NavType.StringType
                },
                navArgument("category") {
                    type = NavType.StringType
                },
                navArgument("genre") {
                    type = NavType.StringType
                }
            )
        ) {
            FilterFilmScreen(
                onBackClicked = {
                    navController.popBackStack()
                },
                onMovieClicked = { id, backDropPath, posterImage ->
                    navController.navigate(
                        Screens.SingleMovieScreen.route + "?backDropPath=$backDropPath?posterImage=$posterImage?movieId=$id"
                    )
                },
                onTvSeriesClicked = { id, backDropPath, posterImage ->
                    navController.navigate(
                        Screens.SingleTvSeriesScreen.route + "?backDropPath=$backDropPath?posterImage=$posterImage?tvSeriesId=$id"
                    )
                }
            )
        }
    }
}

fun NavGraphBuilder.destination(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        content = content,
        arguments = arguments,
        enterTransition = {
            materialSharedAxisZIn(forward = true, durationMillis = 500)
        },
        exitTransition = {
            materialSharedAxisZOut(forward = false, durationMillis = 500)
        },
        popEnterTransition = {
            materialSharedAxisZIn(forward = true, durationMillis = 500)
        },
        popExitTransition = {
            materialSharedAxisZOut(forward = false, durationMillis = 500)
        }
    )
}

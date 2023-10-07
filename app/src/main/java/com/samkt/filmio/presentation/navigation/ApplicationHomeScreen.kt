package com.samkt.filmio.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.samkt.filmio.presentation.homeScreen.HomeScreen
import com.samkt.filmio.presentation.movieScreen.MoviesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationHomeScreen() {
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
                            navController.navigate(navItems.route) {
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
                                painter = painterResource(id = if (isSelected) navItems.selectedIcon else navItems.unselectedIcon),
                                contentDescription = navItems.label
                            )
                        },
                        label = {
                            Text(text = navItems.label)
                        },
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = "home"
        ) {
            destination("home"){
                HomeScreen()
            }
            destination("movies"){
               MoviesScreen()
            }
           destination("tvSeries"){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "TV Series")
                }
            }
            destination("watch_list"){
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


fun NavGraphBuilder.destination(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        content = content,
        arguments = arguments,
        enterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
             fadeOut(animationSpec = tween(300))
        },
    )
}
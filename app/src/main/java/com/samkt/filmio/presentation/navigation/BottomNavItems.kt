package com.samkt.filmio.presentation.navigation

import androidx.annotation.DrawableRes
import com.samkt.filmio.R

data class BottomNavItem(
    val label: String,
    val screen: Screens,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int
)

val navigationItems = listOf(
    BottomNavItem(
        "Home",
        Screens.HomeScreen,
        R.drawable.ic_outline_home,
        R.drawable.ic_filled_home
    ),
    BottomNavItem(
        "Movies",
        Screens.MovieScreen,
        R.drawable.ic_movie_outlined,
        R.drawable.ic_movie_filled
    ),
    BottomNavItem(
        "TV Series",
        Screens.TvSeriesScreen,
        R.drawable.ic_tv_outlined,
        R.drawable.ic_tv_filled
    ),
    BottomNavItem(
        "Watch List",
        Screens.WatchListScreen,
        R.drawable.ic_outline_bookmark,
        R.drawable.ic_filled_bookmark
    )

)

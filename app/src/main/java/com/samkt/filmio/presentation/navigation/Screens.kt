package com.samkt.filmio.presentation.navigation

sealed class Screens(val route:String){
    data object HomeScreen:Screens("home_screen")
    data object MovieScreen:Screens("movie_screen")
    data object TvSeriesScreen:Screens("tv_series_screen")
    data object WatchListScreen:Screens("watchlist")
    data object ApplicationHomePage:Screens("app_home_page")
    data object SingleMovieScreen:Screens("single_movie")
}
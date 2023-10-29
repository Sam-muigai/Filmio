package com.samkt.filmio.presentation.navigation

sealed class Screens(val route:String){
    data object HomeScreen:Screens("home_screen")
    data object MovieScreen:Screens("movie_screen")
    data object TvSeriesScreen:Screens("tv_series_screen")
    data object WatchListScreen:Screens("watchlist")
    data object ApplicationHomePage:Screens("app_home_page")
    data object SingleMovieScreen:Screens("single_movie")
    data object SearchScreen:Screens("search_screen")
    data object SingleTvSeriesScreen:Screens("single_tv_series")
    data object CategoryScreen:Screens("category_screen")
    data object FilterScreen:Screens("filter_screen")
    data object FilteredFilmsScreen:Screens("filtered_film_screen")
}

package com.samkt.filmio.presentation.filterScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterScreenViewModel @Inject constructor():ViewModel(){

    var selectedType by mutableStateOf(types[0])
        private set
    var selectedMovieGenres by mutableStateOf(movieGenres[0])
        private set
    var selectedCategory by mutableStateOf(categories[0])
        private set

    var selectedTvSeriesGenre by mutableStateOf(tvSeriesGenres[0])
        private set

    fun onSelectType(type:String){
        selectedType = type
    }

    fun onSelectMovieGenres(genre:String){
        selectedMovieGenres = genre
    }

    fun onSelectedTvSeries(genre: String){
        selectedTvSeriesGenre = genre
    }

    fun onCategorySelect(category:String){
        selectedCategory = category
    }




}
val types = listOf("Movies", "Tv Series")
val movieGenres = listOf(
    "Action",
    "Adventure",
    "Animation",
    "Comedy",
    "Crime",
    "Documentary",
    "Drama",
    "Family",
    "Fantasy",
    "History",
    "Horror",
    "Music",
    "Mystery",
    "Romance",
    "Science Fiction",
    "TV Movie",
    "Thriller",
    "War",
    "Western"
)
val tvSeriesGenres = listOf(
    "Action & Adventure",
    "Animation",
    "Comedy",
    "Crime",
    "Documentary",
    "Drama",
    "Family",
    "Kids",
    "Mystery",
    "News",
    "Reality",
    "Sci-Fi & Fantasy",
    "Soap",
    "Talk",
    "War & Politics",
    "Western"
)

val categories = listOf(
    "Popular",
    "Top Rated",
    "Upcoming",
    "Trending",
)
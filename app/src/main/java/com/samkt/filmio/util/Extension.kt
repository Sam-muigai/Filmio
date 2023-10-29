package com.samkt.filmio.util

import android.annotation.SuppressLint
import android.net.http.HttpException
import java.io.IOException

fun Int.toMovieGenre(): String {
    return when (this) {
        28 -> "Action"
        12 -> "Adventure"
        16 -> "Animation"
        35 -> "Comedy"
        80 -> "Crime"
        99 -> "Documentary"
        18 -> "Drama"
        10751 -> "Family"
        14 -> "Fantasy"
        36 -> "History"
        27 -> "Horror"
        10402 -> "Music"
        9648 -> "Mystery"
        10749 -> "Romance"
        878 -> "Science Fiction"
        10770 -> "TV Movie"
        53 -> "Thriller"
        10752 -> "War"
        37 -> "Western"
        else -> "Unknown"
    }
}

fun String.toGenreInt(): Int {
    return when (this) {
        "Action" -> 28
        "Adventure" -> 12
        "Animation" -> 16
        "Comedy" -> 35
        "Crime" -> 80
        "Documentary" -> 99
        "Drama" -> 18
        "Family" -> 10751
        "Fantasy" -> 14
        "History" -> 36
        "Horror" -> 27
        "Music" -> 10402
        "Mystery" -> 9648
        "Romance" -> 10749
        "Science Fiction" -> 878
        "TV Movie" -> 10770
        "Thriller" -> 53
        "War" -> 10752
        "Western" -> 37
        else -> 0
    }
}

fun Int.toTvSeriesGenre(): String {
    when (this) {
        10759 -> return "Action & Adventure"
        16 -> return "Animation"
        35 -> return "Comedy"
        80 -> return "Crime"
        99 -> return "Documentary"
        18 -> return "Drama"
        10751 -> return "Family"
        10762 -> return "Kids"
        9648 -> return "Mystery"
        10763 -> return "News"
        10764 -> return "Reality"
        10765 -> return "Sci-Fi & Fantasy"
        10766 -> return "Soap"
        10767 -> return "Talk"
        10768 -> return "War & Politics"
        37 -> return "Western"
        else -> return "Unknown"
    }
}

fun String.toTvSeriesGenreId(): Int {
    return when (this) {
        "Action & Adventure" -> 10759
        "Animation" -> 16
        "Comedy" -> 35
        "Crime" -> 80
        "Documentary" -> 99
        "Drama" -> 18
        "Family" -> 10751
        "Kids" -> 10762
        "Mystery" -> 9648
        "News" -> 10763
        "Reality" -> 10764
        "Sci-Fi & Fantasy" -> 10765
        "Soap" -> 10766
        "Talk" -> 10767
        "War & Politics" -> 10768
        "Western" -> 37
        else -> -1 
    }
}




@SuppressLint("NewApi")
fun Throwable.toErrorMessage(): String {
    return when (this) {
        is HttpException -> "Could not reach the server"
        is IOException -> "No internet connection Please check and try again later."
        else -> message ?: "Unexpected error occurred"
    }
}
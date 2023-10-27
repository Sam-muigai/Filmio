package com.samkt.filmio.util

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import java.io.IOException

fun Int.toGenre(): String {
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


@SuppressLint("NewApi")
fun Throwable.toErrorMessage(): String {
    return when (this) {
        is HttpException -> "Could not reach the server"
        is IOException -> "No internet connection Please check and try again later."
        else -> message ?: "Unexpected error occurred"
    }
}
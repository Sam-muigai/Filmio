package com.samkt.filmio.util

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import java.io.IOException

sealed class Result<out T>(val data:T? = null,val message:String? = null){
    class Success<T>(data: T?):Result<T>(data = data)
    class Error(message: String?):Result<Nothing>(message = message)
}


suspend fun <T> safeApiCall(
    request: suspend ()->T
):Result<T>{
    return try {
        val apiRequest = request.invoke()
        Result.Success(apiRequest)
    }catch (e:Exception){
        when(e){
            is IOException ->{
                Result.Error("No internet connection!!")
            }
            else ->{
                Result.Error("Unexpected error occurred")
            }
        }
    }
}
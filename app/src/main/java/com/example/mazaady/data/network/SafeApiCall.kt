package com.example.mazaady.data.network

import com.example.mazaady.data.entities.DataResult
import com.example.mazaady.data.entities.NetworkFailure
import com.squareup.moshi.JsonAdapter
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class SafeApiCall @Inject constructor(private val jsonAdapter: JsonAdapter<NetworkFailure>){

   suspend operator fun <T> invoke(apiCall: suspend () -> Response<T>): DataResult<T> {

        return try {

            val result = apiCall()
            if (result.isSuccessful) {
                val data = result.body()
                return DataResult.Success(data)
            } else {
                val errorBodyString = result.errorBody()?.string()
                var errorBodyJson: NetworkFailure? = null
                if (errorBodyString != null) {
                    try {
                        errorBodyJson = jsonAdapter.fromJson(errorBodyString)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return DataResult.Error(
                    Exception(
                        errorBodyJson?.message ?: "something went wrong"
                    )
                )
            }
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    val errorBodyString = e.response()?.errorBody()?.string()
                    var errorBodyJson: NetworkFailure? = null
                    if (errorBodyString != null) {
                        try {
                            errorBodyJson = jsonAdapter.fromJson(errorBodyString)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    when (e.code()) {
                        in 300 until 400 -> {
                            DataResult.Error(Exception("Unauthorized"))
                        }
                        in 400 until 500 -> {
                            DataResult.Error(
                                Exception(
                                    errorBodyJson?.message
                                )
                            )
                        }
                        in 500 until 600 -> {
                            DataResult.Error(Exception("Server error please try again."))
                        }
                        else -> {
                            DataResult.Error(Exception("something went wrong"))
                        }
                    }
                }
                is IOException -> {
                    DataResult.Error(Exception("No internet connection"))
                }
                else -> {
                    DataResult.Error(Exception("something went wrong"))
                }
            }
        }
    }
}


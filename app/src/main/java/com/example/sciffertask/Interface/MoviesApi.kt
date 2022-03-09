package com.example.sciffertask.Interface

import com.example.sciffertask.Model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApi {

    @GET("/?")
    suspend fun getMovies(@Query("t") searchTerm: String,
                          @Query("apikey") apikey: String) : Response<MovieModel>
}
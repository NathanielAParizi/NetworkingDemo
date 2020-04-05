package com.example.networkingdemo.Datasource.remote.Retrofit


import com.example.networkingdemo.Model.User.userResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RondomUserService {

    @GET("api/")
    fun getRandomUsers(@Query("results") numOfUsers : String)
            : Call<userResponse>

    @GET("api/")
    fun getRandomUsersByGender(
        @Query("results") numOfUsers : String,
        @Query("gender") gender : String)
            : Call<userResponse>
}
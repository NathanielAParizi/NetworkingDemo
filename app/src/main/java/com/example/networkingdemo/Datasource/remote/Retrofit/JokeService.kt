package com.example.networkingdemo.Datasource.remote.Retrofit

import com.example.networkingdemo.Model.JokeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.File

interface ChuckNorrisService {
    //"https://api.icndb.com/jokes/random"

    companion object{
        fun getChuckNorrisJokeCallService(cacheFile : File) =
            RetrofitHelper().getRetrofitInstance(false, cacheFile).create(ChuckNorrisService::class.java)
    }

    @GET("jokes/{type}")
    fun getRandomJokes(@Path("type") type: String)
            : Call<JokeResponse>
}
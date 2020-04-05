package com.example.networkingdemo.Datasource.remote.Retrofit

import android.util.Log
import com.example.networkingdemo.Datasource.remote.BASE_URL_CHUCKNORRIS_JOKES
import com.example.networkingdemo.Datasource.remote.BASE_URL_RANDOM_USER
import com.example.networkingdemo.Datasource.remote.OkHttpHelper
import com.example.networkingdemo.Datasource.remote.Retrofit.ChuckNorrisService.Companion.getChuckNorrisJokeCallService
import com.example.networkingdemo.Model.JokeResponse
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class RetrofitHelper {

    fun getRetrofitInstance(isRandomUser: Boolean, cacheFile : File): Retrofit {
        val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpHelper(cacheFile).getClient())
        if (isRandomUser) {
            builder.baseUrl(BASE_URL_RANDOM_USER)
        } else {
            builder.baseUrl(BASE_URL_CHUCKNORRIS_JOKES)
        }
        return builder.build()
    }

    fun getRandomUserCallService(cacheFile : File) =
        getRetrofitInstance(true, cacheFile).create(RondomUserService::class.java)


    fun startChuckNorrisRequest(cacheFile: File) {
        getChuckNorrisJokeCallService(cacheFile)
            .getRandomJokes("random")
            .enqueue(object : Callback<JokeResponse> {
                override fun onResponse(call: Call<JokeResponse>, response: Response<JokeResponse>) {
                    EventBus.getDefault().post(response.body())
                }

                override fun onFailure(call: Call<JokeResponse>, t: Throwable) {
                    Log.e("TAG", "ERROR IN RETROFIT -->", t)
                }
            })
    }

}
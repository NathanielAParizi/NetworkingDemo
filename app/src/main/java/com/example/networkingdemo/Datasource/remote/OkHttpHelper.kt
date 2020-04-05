package com.example.networkingdemo.Datasource.remote
import android.util.Log
import com.example.networkingdemo.Model.JokeResponse
import com.example.networkingdemo.Model.User.userResponse

import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit

class OkHttpHelper(val cacheFile : File) {

    fun getClient() : OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val cache = Cache(cacheFile, (1028*10*10))
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .cache(cache)
            .build()
        return okHttpClient
    }

    fun makeAsyncApiCall(url : String){
        val request = Request.Builder().url(url).build()
        getClient().newCall(request).enqueue(object : Callback{
            override fun onResponse(call: Call, response: Response) {
                try{
                    if(url.equals(randomUserFullURL)){
                        val json = response.body?.string()
                        val userResults = Gson().fromJson<userResponse>(json, userResponse::class.java)
                        EventBus.getDefault().post(userResults)
                    } else {
                        val json = response.body?.string()
                        val jokeResponse = Gson().fromJson<JokeResponse>(json, JokeResponse::class.java)
                        EventBus.getDefault().post(jokeResponse)
                    } }catch (e : Exception) {
                    makeSyncApiCall(chuckNorrisJokesURL)

                }

            }

            override fun onFailure(call: Call, e: IOException) {
                makeSyncApiCall(chuckNorrisJokesURL)
                Log.e("ERROR_TAG", "Error in makeAsyncApiCall ---->" ,e)

            }
        })
    }

    fun makeSyncApiCall(url : String) : String{
        val request = Request
            .Builder()
            .url(url)
            .cacheControl(CacheControl.Builder().maxAge(30, TimeUnit.SECONDS).build())
            .build()
        val response = getClient().newCall(request).execute()
        val json = response.body!!.string()
        Log.d("TAG", json)
        return json
    }
}
package com.example.networkingdemo.Datasource.remote

import android.util.Log
import com.example.networkingdemo.Model.ChuckNorrisRepsonse.ChuckNorrisResponse
import com.example.networkingdemo.Model.User.userResponse
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.IOException

class OkHttpHelper(val cacheFile : File) {
    fun getClient() : OkHttpClient {


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val cache = Cache(cacheFile, (1028*10*10)) //10MB


        return okHttpClient
    }

    fun makeAsyncApiCall(url: String) {

        val request = Request.Builder().url(url).build()
        getClient().newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()

                try {

                    val userResults =
                        Gson().fromJson<userResponse>(json, ChuckNorrisResponse::class.java)


                } catch (e: Exception) {

                }

                val userResults = Gson().fromJson<userResponse>(json, userResponse::class.java)
                EventBus.getDefault().post(userResults)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR_TAG", "Eror in makeAsyncApiCall --->", e)

            }
        })

    }

    fun makeSyncApiCall(url: String): String {

        val request = Request.Builder().url(url).build()
        val response = getClient().newCall(request).execute()
        val json = response.body!!.string()
        Log.d("TAG",json)

        return json
    }

}

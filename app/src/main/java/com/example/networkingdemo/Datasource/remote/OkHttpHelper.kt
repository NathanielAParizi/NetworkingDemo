package com.example.networkingdemo.Datasource.remote

import android.util.Log
import com.example.networkingdemo.Model.User.userResponse
import com.google.gson.Gson
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import java.io.IOException

class OkHttpHelper {
    private fun getClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder().build()
        return okHttpClient
    }

    fun makeAsyncApiCall(url: String) {

        val request = Request.Builder().url(url).build()
        getClient().newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                val userResults = Gson().fromJson<userResponse>(json, userResponse::class.java)
                EventBus.getDefault().post(userResults)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR_TAG", "Eror in makeAsyncApiCall --->",e)

            }
        })

    }

    fun makeSyncApiCall(url: String): String {

      return ""
    }

}

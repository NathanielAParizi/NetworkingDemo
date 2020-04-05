package com.example.networkingdemo.Datasource.remote

import android.os.AsyncTask
import com.example.networkingdemo.Model.ChuckNorrisRepsonse.ChuckNorrisResponse
import com.example.networkingdemo.Model.User.userResponse
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import java.io.File

class OkHttpAsyncTask(val cacheFile : File) : AsyncTask<Void, Void, String>() {

    lateinit var userResponse: userResponse


    override fun doInBackground(vararg params: Void?): String {


        try {


            val okHttpHelper = OkHttpHelper(cacheFile)
            val randomUserURL = "https://randomuser.me/api/?results=10"
            val json = okHttpHelper.makeSyncApiCall(randomUserURL)
            val userResponse = Gson().fromJson<userResponse>(json, userResponse::class.java)
            return json

        } catch (e: Exception) {

            val okHttpHelper = OkHttpHelper(cacheFile)
            val json = okHttpHelper.makeSyncApiCall(chuckNorrisJokesURL)
            return json

        }

    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        if (::userResponse.isInitialized) {
            EventBus.getDefault().post(userResponse)
        } else {
            val chuckNorrisResponse =
                Gson().fromJson<ChuckNorrisResponse>(result, ChuckNorrisResponse::class.java)
            EventBus.getDefault().post(chuckNorrisResponse)
        }
    }
}
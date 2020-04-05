package com.example.networkingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkingdemo.Datasource.remote.*
import com.example.networkingdemo.Datasource.remote.Retrofit.RetrofitHelper
import com.example.networkingdemo.Model.ChuckNorrisRepsonse.ChuckNorrisResponse
import com.example.networkingdemo.Model.JokeResponse
import com.example.networkingdemo.Model.User.userResponse
import com.example.networkingdemo.View.Adapter.UserAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.btnExecuteHttpUrlConnCall -> executeHttpUrlConnCall()
            R.id.btnExecuteAsyncOkHttp -> executeAsyncOkHttpCall()
            R.id.btnExecuteSyncOkHttp -> executeSyncOkHttpCall()
            R.id.btnExecuteAsyncRetrofit -> executeAsyncRetrofitCall()
        }
    }

    fun executeAsyncOkHttpCall() {
        val okHttpHelper = OkHttpHelper(cacheDir)
        try {
            okHttpHelper.makeAsyncApiCall(randomUserFullURL)
        } catch(e : Exception) {
            okHttpHelper.makeAsyncApiCall(chuckNorrisJokesURL)
        }
    }

    fun executeSyncOkHttpCall() {
        val okHttpAsyncTsk = OkHttpAsyncTask(cacheDir)
        okHttpAsyncTsk.execute()
    }

    fun executeAsyncRetrofitCall() {
        val retrofitHelper = RetrofitHelper()
        retrofitHelper.startChuckNorrisRequest(cacheDir)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserResponse(userResponse: userResponse) {
        rvUserList.layoutManager = LinearLayoutManager(this)
        rvUserList.adapter = UserAdapter(userResponse.results)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onJokeResponse(jokeResponse: JokeResponse) {
        Log.d("TAG", jokeResponse.value.joke)
        Toast.makeText(this, jokeResponse.value.joke, Toast.LENGTH_LONG).show()
    }

    fun executeHttpUrlConnCall() {
        val randomUserURL = "https://randomuser.me/api/?results=10"
        val httpUrlConnectionHelper = HttpUrlConnectionHelper()
        var jsonString = ""
        Thread(
            Runnable
            {
                jsonString = httpUrlConnectionHelper.getResponse(randomUserURL)
                Log.d("TAG", jsonString)

                if(jsonString.isNotBlank()) {
                    val userResponse = Gson().fromJson<userResponse>(jsonString, userResponse::class.java)
                    Log.d("TAG", "FIRST RESPONSES FIRST NAME = ${userResponse.results[0].name.first}")
                    runOnUiThread {
                        rvUserList.layoutManager = LinearLayoutManager(this)
                        rvUserList.adapter = UserAdapter(userResponse.results)
                    }
                }
            }
        ).start()



    }
}
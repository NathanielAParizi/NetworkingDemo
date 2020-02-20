package com.example.networkingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkingdemo.Datasource.remote.HttpUrlConnectionHelper
import com.example.networkingdemo.Model.User.userResponse
import com.example.networkingdemo.View.Adapter.UserAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.button -> executeHttpUrlConnCall()
        }

    }

    private fun executeHttpUrlConnCall() {

        val randomUserURL = "https://randomuser.me/api/?results=10"
        val httpURLConnectionHelper = HttpUrlConnectionHelper()
        var jsonString = ""
        Thread(
            Runnable
            {
                jsonString = httpURLConnectionHelper.getResponse(randomUserURL)
                Log.d("TAG",jsonString)

                if (jsonString.isNotBlank()){
                    val responseFromUser = Gson().fromJson<userResponse>(jsonString, userResponse::class.java)
                    Log.d("TAG","FIRST RESPONSES FIRST NAME = ${responseFromUser.results[0].name.first}")


                   runOnUiThread{

                       rvList.layoutManager = LinearLayoutManager(this)
                       rvList.adapter = UserAdapter(responseFromUser.results)

                   }




                }
            }

        ).start()




    }
}

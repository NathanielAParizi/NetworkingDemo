package com.example.networkingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {

        when(view.id){
            R.id.button -> executeHttpUrlConnCall()
        }

    }

    private fun executeHttpUrlConnCall() {
        val randomUserURL = "https://randomuser.me/api/?results=10"
        val httpUrlConnectionHelper = HttpUrlConnectionHelper()
        Thread(Runnable{
            Log.d("TAG", httpUrlConnectionHelper.getResponse(randomUserURL).toString())
        }).start()


    }
}

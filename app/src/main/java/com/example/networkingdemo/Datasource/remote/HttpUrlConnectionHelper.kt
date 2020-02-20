package com.example.networkingdemo.Datasource.remote

import android.net.Uri
import java.net.HttpURLConnection
import java.net.URL

class HttpUrlConnectionHelper{
    fun getResponse(url : String) : String {

        var returnString = ""
        val url = URL(url)
        val connection = url.openConnection() as HttpURLConnection

        val stream = connection.inputStream
        var read = stream.read()

        while (read > 0){
            returnString = "$returnString${read.toChar()}"
            read = stream.read()

        }

        stream.close()
        return returnString

    }


}
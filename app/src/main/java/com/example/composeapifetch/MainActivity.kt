package com.example.composeapifetch

import ApiDataItem
import ApiInterface
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.example.composeapifetch.ui.theme.ComposeApiFetchTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            ComposeApiFetchTheme {

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Button(onClick = { getMyData() }) {
                        Text(text = "Get API Data")
                    }
                }
                }
            }
        }
    }



    private fun getMyData() {
        val BASE_URL = "https://jsonplaceholder.typicode.com/"
     val retrofitBuilder = Retrofit.Builder()
         .addConverterFactory(GsonConverterFactory.create())
         .baseUrl(BASE_URL)
         .build()
         .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getPosts()
        retrofitData.enqueue(object : Callback<List<ApiDataItem>> {
            override fun onResponse(
                call: Call<List<ApiDataItem>>,
                response: Response<List<ApiDataItem>>
            ) {
                val stringBuilder = StringBuilder()
                for (i in response.body()!!) {
                   stringBuilder.append(i.id)
                    Log.d("TAG", "onResponse: $stringBuilder")
                }
            }
            override fun onFailure(call: Call<List<ApiDataItem>>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }
        })
    }


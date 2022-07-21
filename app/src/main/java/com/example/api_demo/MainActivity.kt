package com.example.api_demo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.ResponseDelivery
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.api_demo.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var dataList = ArrayList<MyDataModel>()
    lateinit var recyclerView: RecyclerView
    var myAdapter: CustomAdapter ? = null
    val Base="https://jsonplaceholder.typicode.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyclerView = findViewById(R.id.reuse)


        getData()
    }

    private fun getData() {

        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        var client: OkHttpClient=OkHttpClient
            .Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()


        var retrofitBuilder=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(Base)
            .build()
            .create(ApiInterface::class.java)
        var retroData=retrofitBuilder.getData()

        retroData.enqueue(object : Callback<List<MyDataModel>?>{
            override fun onResponse(call: Call<List<MyDataModel>?>, response: retrofit2.Response<List<MyDataModel>?>)
            {
                var Res = response.body()
                var mystr= StringBuilder()
                if (Res != null) {
                    for (Mydata in Res)
                    {
                        mystr.append(Mydata.id)
                        mystr.append(" ")
                        mystr.append(Mydata.title)
                        mystr.append(" ")
                       mystr.append("\n")
                    }
                }
                myAdapter = CustomAdapter(Res!!)
                recyclerView.adapter = myAdapter

                binding.ans.text=mystr
            }

            override fun onFailure(call: Call<List<MyDataModel>?>, t: Throwable) {
                Log.d("MainActivity","onFailure"+t.message)
            }

        })

    }

}
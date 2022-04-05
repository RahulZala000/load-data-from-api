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
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var dataList = ArrayList < MydataItem > ()
    lateinit var recyclerView: RecyclerView
    var myAdapter: CustomAdapter ? = null
    val Base="https://jsonplaceholder.typicode.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getData()

    }

    private fun getData() {
        var retrofitBuilder=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Base)
            .build()
            .create(ApiInterface::class.java)
        var retroData=retrofitBuilder.getData()

        retroData.enqueue(object : Callback<List<MydataItem>?>{
            override fun onResponse(call: Call<List<MydataItem>?>, response: retrofit2.Response<List<MydataItem>?>)
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
                      /*  if(Mydata.id%10==0)
                            mystr.append("\n")*/

                    }
                }

                binding.ans.text=mystr
            }

            override fun onFailure(call: Call<List<MydataItem>?>, t: Throwable) {
                Log.d("MainActivity","onFailure"+t.message)
            }

        })

    }

}
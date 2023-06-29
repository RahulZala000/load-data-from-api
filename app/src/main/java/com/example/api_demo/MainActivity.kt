package com.example.api_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.api_demo.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var dataList:List<MydataItem>? =  ArrayList()
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
                dataList = response.body()
                var mystr= StringBuilder()
               /* if (Res != null) {
                    for (Mydata in Res)
                    {
                        mystr.append(Mydata.id)
                        mystr.append(" ")
                        mystr.append(Mydata.title)
                        mystr.append(" ")
                       mystr.append("\n")

                      *//*  if(Mydata.id%10==0)
                            mystr.append("\n")*//*

                    }
                }*/
                myAdapter= CustomAdapter(dataList = dataList!!)
                binding.reuse.layoutManager=LinearLayoutManager(this@MainActivity)
                binding.reuse.adapter=myAdapter

              //  binding.ans.text=mystr
            }

            override fun onFailure(call: Call<List<MydataItem>?>, t: Throwable) {
                Log.d("MainActivity","onFailure"+t.message)
            }

        })

    }

}
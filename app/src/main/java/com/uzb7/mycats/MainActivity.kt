package com.uzb7.mycats

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.uzb7.mycats.adapter.CatAdapter
import com.uzb7.mycats.data.remote.ApiClient
import com.uzb7.mycats.databinding.ActivityMainBinding
import com.uzb7.mycats.model.Cats
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var list=ArrayList<Cats>()
    lateinit var adapter: CatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply{
            loadCats()
            adapter = CatAdapter(list)
            rvCats.adapter=adapter
            rvCats.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        }
    }

    private fun loadCats() {
        ApiClient.apiService.getCatsById(20,"abys").enqueue(object :Callback<ArrayList<Cats>>{
            override fun onResponse(
                call: Call<ArrayList<Cats>>,
                response: Response<ArrayList<Cats>>
            ) {
                if(response.isSuccessful){
                    list=response.body()!!
                    adapter.submitList(list)
                }
            }

            override fun onFailure(call: Call<ArrayList<Cats>>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }
}
package com.uzb7.mycats.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.uzb7.mycats.adapter.CatAdapter
import com.uzb7.mycats.data.remote.ApiClient
import com.uzb7.mycats.databinding.ActivityMainBinding
import com.uzb7.mycats.model.Cats
import com.uzb7.mycats.utils.EndlessRecyclerViewScrollListener
import com.uzb7.mycats.utils.hide
import com.uzb7.mycats.utils.show
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var list = ArrayList<Cats>()
    var page = 0
    lateinit var adapter: CatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            loadNextPage()

            refreshLayout.setOnRefreshListener {
                refreshLayout.isRefreshing=false
                list.clear()
                loadNextPage()
            }

            adapter = CatAdapter(list)
            rvCats.adapter = adapter
            val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvCats.layoutManager = manager
            val scrollListener = object : EndlessRecyclerViewScrollListener(manager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    loadNextPage()
                }
            }
            rvCats.addOnScrollListener(scrollListener)

        }
    }

    private fun loadNextPage() {
        binding.loading.show()
        ApiClient.apiService.getCatsByPage("beng", getPages())
            .enqueue(object : Callback<ArrayList<Cats>> {

                override fun onResponse(
                    call: Call<ArrayList<Cats>>,
                    response: Response<ArrayList<Cats>>
                ) {
                    if (response.isSuccessful) {
                        adapter.submitList(response.body()!!)
                        binding.loading.hide()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Cats>>, t: Throwable) {
                }


            })
    }

    private fun getPages(): Int {
        return page++
    }

    private fun loadCats() {
        binding.loading.show()
        ApiClient.apiService.getCatsById(20, "beng").enqueue(object : Callback<ArrayList<Cats>> {
            override fun onResponse(
                call: Call<ArrayList<Cats>>,
                response: Response<ArrayList<Cats>>
            ) {
                if (response.isSuccessful) {
                    list = response.body()!!
                    adapter.submitList(list)
                    binding.loading.hide()
                }
            }

            override fun onFailure(call: Call<ArrayList<Cats>>, t: Throwable) {

            }


        })
    }
}



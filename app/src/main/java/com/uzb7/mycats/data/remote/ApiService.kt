package com.uzb7.mycats.data.remote

import com.uzb7.mycats.model.Cats
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //https://thecatapi.com/thanks

    @GET("images/search")
    fun getCatsById(
        @Query("limit") limit: Int,
        @Query("breed_ids") breed_ids: String
    ): Call<ArrayList<Cats>>

    @GET("images/search")
    fun getCatsByPage(
        @Query("breed_ids") breed_ids: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Call<ArrayList<Cats>>


}
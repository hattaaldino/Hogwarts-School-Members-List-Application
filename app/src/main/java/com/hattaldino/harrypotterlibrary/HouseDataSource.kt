package com.hattaldino.harrypotterlibrary

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HouseDataSource {

    @GET("houses")
    fun allHouses(
        @Query("key")
        key: String = BuildConfig.API_KEY
    ): Call<List<HouseResponse>>

}
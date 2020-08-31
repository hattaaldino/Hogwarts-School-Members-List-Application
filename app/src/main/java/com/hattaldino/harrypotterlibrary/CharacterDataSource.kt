package com.hattaldino.harrypotterlibrary

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterDataSource {

    @GET("characters")
    fun memberByHouse(
        @Query("key")
        key: String = BuildConfig.API_KEY,

        @Query("house")
        house: String
    ): Call<List<CharacterResponse>>

    @GET("characters/{id}")
    fun getCharacter(
        @Path("id")
        charId: String,

        @Query("key")
        key: String = BuildConfig.API_KEY
    ): Call<CharacterResponse>
}
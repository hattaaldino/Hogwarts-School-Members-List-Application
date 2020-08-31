package com.hattaldino.harrypotterlibrary

import com.google.gson.annotations.SerializedName

data class HouseResponse(
    @SerializedName("_id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("mascot")
    val mascot: String,

    @SerializedName("headOfHouse")
    val head: String,

    @SerializedName("school")
    val school: String
)
package com.hattaldino.harrypotterlibrary

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("_id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("house")
    val house: String,

    @SerializedName("school")
    val school: String,

    @SerializedName("species")
    val species: String,

    @SerializedName("bloodStatus")
    val bloodStatus: String
)
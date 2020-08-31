package com.hattaldino.harrypotterlibrary

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterActivity : AppCompatActivity() {
    private lateinit var idChar: String
    private lateinit var logoHouse: ImageView
    private lateinit var nameChar: TextView
    private lateinit var roleChar: TextView
    private lateinit var houseChar: TextView
    private lateinit var schoolChar: TextView
    private lateinit var speciesChar: TextView
    private lateinit var bloodChar: TextView

    companion object{
        const val REF = "ref"
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        idChar = intent.getStringExtra(REF) ?: "0"
        logoHouse = findViewById(R.id.img_house_logo)
        nameChar = findViewById(R.id.tv_member_name)
        roleChar = findViewById(R.id.tv_member_role)
        houseChar = findViewById(R.id.tv_house_name)
        schoolChar = findViewById(R.id.tv_school_name)
        speciesChar = findViewById(R.id.tv_species)
        bloodChar = findViewById(R.id.tv_blood_status)

        val service = NetworkProvider.providesHttpAdapter().create(CharacterDataSource::class.java)
        service.getCharacter(charId = idChar).enqueue(object: Callback<CharacterResponse>{
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                val result = response.body()
                setAllView(result)
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                Log.e(CharacterActivity::class.java.simpleName, "${t.printStackTrace()}")
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setAllView(char: CharacterResponse?){
        nameChar.text = char?.name ?: "Unknown"
        roleChar.text = char?.role ?: "Unknown"
        houseChar.text = char?.house ?: "Unknown"
        schoolChar.text = char?.school ?: "Unknown"
        speciesChar.text = char?.species ?: "Unknown"
        bloodChar.text = char?.bloodStatus ?: "Unknown"

        when(char?.house){
            "Gryffindor" -> {
                setHouseLogo(
                    R.drawable.toppng_com_harrypotter_gryffindor_lion_hogwarts_wizardfreetoedit_gryffindor_hogwarts_houses_348x423
                )
            }
            "Ravenclaw" -> {
                setHouseLogo(
                    R.drawable.toppng_com_ravenclaw_sticker_hogwarts_house_crest_ravenclaw_978x1200
                )
            }
            "Slytherin" -> {
                setHouseLogo(
                    R.drawable.toppng_com_slytherin_sticker_687x819
                )
            }
            "Hufflepuff" -> {
                setHouseLogo(
                    R.drawable.toppng_com_escudo_gryffindor_png_hufflepuff_house_369x452
                )
            }
            else -> {
                setHouseLogo(
                    R.drawable.ic_hogwarts
                )
            }
        }
    }

    private fun setHouseLogo(logo: Int) {
        Glide.with(this)
            .load(logo)
            .into(logoHouse)
    }
}
package com.hattaldino.harrypotterlibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var pbHome: ProgressBar
    private lateinit var rvHouses: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pbHome = findViewById(R.id.pb_main)
        rvHouses = findViewById(R.id.rv_houses)

        val service = NetworkProvider.providesHttpAdapter().create(HouseDataSource::class.java)
        service.allHouses().enqueue(object: Callback<List<HouseResponse>> {
            override fun onResponse(call: Call<List<HouseResponse>>, response: Response<List<HouseResponse>>) {
                pbHome.visibility = View.GONE

                val result = response.body()
                displayHouse(result)
            }

            override fun onFailure(call: Call<List<HouseResponse>>, t: Throwable) {
                Log.e(MainActivity::class.java.simpleName, "${t.printStackTrace()}")
            }
        })
    }

    private fun moveToMembersActivity(ref: String){
        val toMemberActivityWithID = Intent(this@MainActivity, MembersActivity::class.java)
        toMemberActivityWithID.putExtra(MembersActivity.REF, ref)

        startActivity(toMemberActivityWithID)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_profile -> {
                val toProfileActivity = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(toProfileActivity)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun displayHouse(houseList: List<HouseResponse>?){
        val houseAdapter = HouseAdapter(houseList ?: emptyList())
        this.rvHouses.adapter = houseAdapter

        houseAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onClick(ref: String) {
                moveToMembersActivity(ref)
            }
        })

    }
}
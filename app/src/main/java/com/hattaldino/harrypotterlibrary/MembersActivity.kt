package com.hattaldino.harrypotterlibrary

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MembersActivity : AppCompatActivity(){
    private lateinit var pbMember: ProgressBar
    private lateinit var rvMember: RecyclerView

    private lateinit var houseName: String
    private val SCROOL_STATE = "scrool_state"

    companion object{
        const val REF = "ref"
    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_members)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        houseName = intent.getStringExtra(REF) ?: "0"
        pbMember = findViewById(R.id.pb_members)
        rvMember = findViewById(R.id.rv_member)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        val recyclerViewScroolState = savedInstanceState?.getParcelable<Parcelable>(SCROOL_STATE)
        rvMember.layoutManager?.onRestoreInstanceState(recyclerViewScroolState)

        val service = NetworkProvider.providesHttpAdapter().create(CharacterDataSource::class.java)
        service.memberByHouse(house = houseName).enqueue(object: Callback<List<CharacterResponse>>{
            override fun onResponse(
                call: Call<List<CharacterResponse>>,
                response: Response<List<CharacterResponse>>
            ) {
                pbMember.visibility = View.GONE

                val result = response.body()
                displayMember(result)
            }

            override fun onFailure(call: Call<List<CharacterResponse>>, t: Throwable) {
                Log.e(MembersActivity::class.java.simpleName, "${t.printStackTrace()}")
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        val currentScrollState = rvMember.layoutManager?.onSaveInstanceState()
        outState.putParcelable(SCROOL_STATE, currentScrollState)
    }

    private fun moveToCharacterActivity(ref: String) {
        val toCharacterActivity = Intent(this@MembersActivity, CharacterActivity::class.java)
        toCharacterActivity.putExtra(CharacterActivity.REF, ref)

        startActivity(toCharacterActivity)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun displayMember(memberList: List<CharacterResponse>?){
        this.rvMember.addItemDecoration(DividerItemDecoration(this@MembersActivity, DividerItemDecoration.VERTICAL))

        val memberAdapter = MemberAdapter(memberList ?: emptyList())
        this.rvMember.adapter = memberAdapter

        memberAdapter.setOnItemClickCallback(object: OnItemClickCallback{
            override fun onClick(ref: String) {
                moveToCharacterActivity(ref)
            }
        })
    }
}
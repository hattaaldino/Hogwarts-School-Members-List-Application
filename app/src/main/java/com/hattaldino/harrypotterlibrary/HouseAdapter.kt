package com.hattaldino.harrypotterlibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class HouseAdapter(private val houseList: List<HouseResponse>): Adapter<HouseAdapter.HouseViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        return HouseViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.cardview_house, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        holder.Bind(houseList[position])
    }

    override fun getItemCount(): Int {
        return houseList.count()
    }

    inner class HouseViewHolder(itemView: View): ViewHolder(itemView) {

        fun Bind(house: HouseResponse){
            with(itemView){

                when(house.mascot){
                    "lion" -> {
                        setHouseLogo(
                            this,
                            R.drawable.toppng_com_harrypotter_gryffindor_lion_hogwarts_wizardfreetoedit_gryffindor_hogwarts_houses_348x423
                        )
                    }
                    "eagle" -> {
                        setHouseLogo(
                            this,
                            R.drawable.toppng_com_ravenclaw_sticker_hogwarts_house_crest_ravenclaw_978x1200
                        )
                    }
                    "serpent" -> {
                        setHouseLogo(
                            this,
                            R.drawable.toppng_com_slytherin_sticker_687x819
                        )
                    }
                    "badger" -> {
                        setHouseLogo(
                            this,
                            R.drawable.toppng_com_escudo_gryffindor_png_hufflepuff_house_369x452
                        )
                    }
                    else -> {
                        setHouseLogo(
                            this,
                            R.drawable.ic_hogwarts
                        )
                    }

                }

                val idHouse = this.findViewById<TextView>(R.id.tv_house_id)
                idHouse.text = house.id

                val nameHouse = this.findViewById<TextView>(R.id.tv_house_name)
                nameHouse.text = house.name

                val headHouse = this.findViewById<TextView>(R.id.tv_house_head)
                headHouse.text = house.head

                val school = this.findViewById<TextView>(R.id.tv_school_name)
                school.text = house.school

                val displayMemberBtn = this.findViewById<Button>(R.id.btn_all_member)
                displayMemberBtn.setOnClickListener {
                    onItemClickCallback.onClick(house.name)
                }
            }
        }

        private fun setHouseLogo(view: View, logo: Int) {
            Glide.with(view.context)
                .load(logo)
                .into(view.findViewById<ImageView>(R.id.img_house_logo))
        }
    }
}
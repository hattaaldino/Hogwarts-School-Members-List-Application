package com.hattaldino.harrypotterlibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter

class MemberAdapter(private val memberList: List<CharacterResponse>): Adapter<MemberAdapter.CharacterViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.listview_member, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.Bind(memberList[position])
    }

    override fun getItemCount(): Int {
        return memberList.count()
    }

    inner class CharacterViewHolder(itemView: View): ViewHolder(itemView){

        fun Bind(member: CharacterResponse){
            with(itemView){
                val idMember = this.findViewById<TextView>(R.id.tv_member_id)
                idMember.text = member.id

                val nameMember = this.findViewById<TextView>(R.id.tv_member_name)
                nameMember.text = member.name

                val roleMember = this.findViewById<TextView>(R.id.tv_member_role)
                roleMember.text = member.role

                this.setOnClickListener {
                    onItemClickCallback.onClick(member.id)
                }
            }
        }
    }

}
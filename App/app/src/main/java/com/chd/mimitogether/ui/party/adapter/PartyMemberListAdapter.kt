package com.chd.mimitogether.ui.party.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.R
import com.chd.mimitogether.dto.UserRequest
import kotlinx.android.synthetic.main.item_partymember.view.*

class PartyMemberListAdapter : RecyclerView.Adapter<Holder>(){

    var memberList = mutableListOf<UserRequest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_partymember,parent,false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val member = memberList.get(position)
        holder.setMember(member)
    }

    override fun getItemCount(): Int {
        return memberList.size
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setMember(member: UserRequest){
        itemView.party_member_name.text = member.uiName
    }
}
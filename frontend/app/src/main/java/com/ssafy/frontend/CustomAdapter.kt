package com.ssafy.frontend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_recycler.view.*

class CustomAdapter : RecyclerView.Adapter<Holder>(){

    var userList = mutableListOf<RepositoryItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler,parent,false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = userList.get(position)
        holder.setUser(user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }


}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setUser(user: RepositoryItem){
        itemView.textName.text = user.name
        itemView.textId.text = user.node_id
        Glide.with(itemView).load(user.owner.avatar_url).into(itemView.imageAvatar)
    }
}
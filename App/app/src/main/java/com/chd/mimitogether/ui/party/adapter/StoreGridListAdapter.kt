package com.chd.mimitogether.ui.party.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.Store
import kotlinx.android.synthetic.main.item_storelist.view.*

class StoreGridListAdapter : RecyclerView.Adapter<Holder3>(){

    var storeList = mutableListOf<Store>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder3 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_storelist,parent,false)

        return Holder3(view)
    }

    override fun onBindViewHolder(holder: Holder3, position: Int) {
        val store = storeList.get(position)
        holder.setStore(store)
    }

    override fun getItemCount(): Int {
        return storeList.size
    }
}

class Holder3(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setStore(store: Store){
        itemView.grid_store_name.text = store.name
        Glide.with(itemView.context).load(store?.img).into(itemView.grid_store_image)
    }
}
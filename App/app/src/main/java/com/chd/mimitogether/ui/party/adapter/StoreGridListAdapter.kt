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
        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it,position)
        }
    }

    override fun getItemCount(): Int {
        return storeList.size
    }

    //클릭 인터페이스 정의
    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}

class Holder3(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setStore(store: Store){
        itemView.grid_store_name.text = store.name
        Glide.with(itemView.context).load(store?.img).into(itemView.grid_store_image)
    }
}
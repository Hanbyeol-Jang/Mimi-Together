package com.chd.mimitogether.ui.party.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.R
import com.chd.mimitogether.dto.jusoSearch.Juso
import kotlinx.android.synthetic.main.item_addresslist.view.*

class PartyAddressListAdapter : RecyclerView.Adapter<Holder6>() {

    var jusoList = mutableListOf<Juso>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder6 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_addresslist,parent,false)

        return Holder6(view)
    }

    override fun onBindViewHolder(holder: Holder6, position: Int) {
        val juso = jusoList.get(position)
        holder.setJuso(juso)
        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it,position)
        }
    }

    override fun getItemCount(): Int {
        return jusoList.size
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

class Holder6(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setJuso(juso: Juso){
        itemView.textview_address.text = juso.jibunAddr
        itemView.textview_address2.text = juso.roadAddr
    }
}
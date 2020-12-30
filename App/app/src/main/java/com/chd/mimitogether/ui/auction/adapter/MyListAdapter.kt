package com.chd.mimitogether.ui.auction.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.auction.dto.Auction
import kotlinx.android.synthetic.main.auction_item_mylist.view.*

class MyListAdapter: RecyclerView.Adapter<MyListAdapter.Holder>() {

    val contents = mutableListOf<Auction>()

    class Holder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        fun setContent(auction: Auction) {
            itemView.dining_location.text = auction.dnLocation
            itemView.dining_name.text = auction.dnName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.auction_item_mylist, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val content = contents[position]
        holder.setContent(content)
    }

    override fun getItemCount(): Int {
        return contents.size
    }

}
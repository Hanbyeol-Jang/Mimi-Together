package com.chd.mimitogether.ui.party.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.Menu
import kotlinx.android.synthetic.main.item_menulist.view.*

class AllMenuListAdapter: RecyclerView.Adapter<Holder5>(){

    var menuList = mutableListOf<Menu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder5 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menulist,parent,false)

        return Holder5(view)
    }

    override fun onBindViewHolder(holder: Holder5, position: Int) {
        val menu = menuList.get(position)
        holder.setMenu(menu)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }
}

class Holder5(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setMenu(menu: Menu){

        itemView.menu_name.text = menu.name
        if(menu.price != null){
            itemView.menu_price.text = menu.price
        }else{
            itemView.menu_price.text = ""
            itemView.dotline.isVisible = false
        }
    }
}
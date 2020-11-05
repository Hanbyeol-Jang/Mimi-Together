package com.chd.mimitogether.ui.party.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.Party
import kotlinx.android.synthetic.main.item_partylist.view.*

class PartyListAdapter : RecyclerView.Adapter<Holder2>() {

    var partyList = mutableListOf<Party>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_partylist,parent,false)

        return Holder2(view)
    }

    override fun onBindViewHolder(holder: Holder2, position: Int) {
        val party = partyList.get(position)
        holder.setParty(party)
        holder.itemView.setOnClickListener {
            itemClickListner.onClick(it,position)
        }
    }

    //클릭 인터페이스 정의
    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    override fun getItemCount(): Int {
        return partyList.size
    }

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

}

class Holder2(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setParty(party: Party){
        itemView.party_list_name.text = party.ptName
        itemView.party_list_membercount.text = party.userList.size.toString()+"인"
    }
}
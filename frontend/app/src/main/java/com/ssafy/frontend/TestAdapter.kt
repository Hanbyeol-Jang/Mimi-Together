package com.ssafy.frontend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.item_user.view.*

class TestAdapter : RecyclerView.Adapter<TestHolder>(){

    var tempList = mutableListOf<User>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)

        return TestHolder(view)
    }

    override fun onBindViewHolder(holder: TestHolder, position: Int) {
        val user = tempList.get(position)
        holder.setUser(user)
    }

    override fun getItemCount(): Int {
        return tempList.size
    }


}

class TestHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setUser(user: User){
        itemView.tempId.text = user.id
        itemView.temp.text = user.temp
        itemView.temp2.text = user.temp2

        Log.d("mylog",user.id)
        Log.d("mylog",user.temp)
        Log.d("mylog",user.temp2)
    }
}
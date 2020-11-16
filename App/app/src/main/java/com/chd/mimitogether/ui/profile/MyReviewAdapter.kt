package com.chd.mimitogether.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.Review.ReviewRequest
import kotlinx.android.synthetic.main.item_myreview.view.*

class MyReviewAdapter : RecyclerView.Adapter<Holder8>(){

    var reviewlist = mutableListOf<ReviewRequest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder8 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_myreview,parent,false)

        return Holder8(view)
    }

    override fun onBindViewHolder(holder: Holder8, position: Int) {
        val review = reviewlist.get(position)
        holder.setReview(review)
        holder.itemView.myreview_gostore.setOnClickListener {
            itemClickListner.onClick(it,position)
        }
    }

    override fun getItemCount(): Int {
        return reviewlist.size
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

class Holder8(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun setReview(review: ReviewRequest){
        itemView.myreview_uname.text = review.userName
        itemView.myreview_text.text = review.review
        itemView.myreview_rating.rating = review.rating.toFloat()
    }
}
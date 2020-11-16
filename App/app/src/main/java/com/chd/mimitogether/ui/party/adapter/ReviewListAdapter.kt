package com.chd.mimitogether.ui.party.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chd.mimitogether.R
import com.chd.mimitogether.dto.UserRequest
import com.chd.mimitogether.ui.party.dto.Review.Content
import kotlinx.android.synthetic.main.item_partymember.view.*
import kotlinx.android.synthetic.main.item_review.view.*
import kotlinx.android.synthetic.main.item_storelist.view.*

class ReviewListAdapter: RecyclerView.Adapter<Holder7>() {

    val reviewList = mutableListOf<Content>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder7 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review,parent,false)

        return Holder7(view)
    }

    override fun onBindViewHolder(holder: Holder7, position: Int) {
        val review = reviewList.get(position)
        holder.setReview(review)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }


}

class Holder7(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun setReview(review: Content){
        itemView.review_uname.text = review.userName
        itemView.review_rating.rating = review.rating.toFloat()
        itemView.review_text.text = review.review
//        Glide.with(itemView.context).load(review?.img).into(itemView.review_image)
    }
}
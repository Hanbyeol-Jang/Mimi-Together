package com.chd.mimitogether.ui.auction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R

class AuctionDevelopFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainActivity = activity as MainActivity
        mainActivity.setToolbarTitle("회식 역경매")
        return inflater.inflate(R.layout.auction_fragment_develop, container, false)
    }

}
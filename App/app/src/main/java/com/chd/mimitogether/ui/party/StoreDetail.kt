package com.chd.mimitogether.ui.party

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.adapter.ViewPagerAdapter
import com.chd.mimitogether.ui.party.dto.Party
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class StoreDetail : Fragment() {

    val tabLayoutTextArray = arrayOf("메뉴","가게정보","위치","리뷰")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_storedetail, container, false)
        val mainActivity : MainActivity = activity as MainActivity
        val store = mainActivity.loadStore()
        val party : Party = mainActivity.selectParty!!

        val stName : TextView = root.findViewById(R.id.storedetail_name)
        val stStar : RatingBar = root.findViewById(R.id.store_rating_star)
        val stStarNum : TextView = root.findViewById(R.id.store_rating_text)
        val callbtn : Button = root.findViewById(R.id.store_call_btn)
        val promisebtn : Button = root.findViewById(R.id.store_promise_btn)

        stName.text = store.name
        stStar.rating = store.rating.toFloat()
        stStarNum.text = "("+store.rating+")"

        val tablayout : TabLayout = root.findViewById(R.id.store_tabLayout)
        val viewpager : ViewPager2 = root.findViewById(R.id.store_viewpager)

        val adapter = ViewPagerAdapter(requireActivity())
        val fragments = listOf<Fragment>( StoreMenuFragment(), StoreInformationFragment(), StoreMapFragment(),ReviewFragment())
        adapter.fragments.addAll(fragments)

        viewpager.adapter = adapter

        TabLayoutMediator(tablayout,viewpager){tab,position->
            tab.text = tabLayoutTextArray[position]
        }.attach()

        callbtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+store.tel))
            startActivity(intent)
        }

        val uid = mainActivity.loadData("uid")

        promisebtn.setOnClickListener {
            if(party.userList[0].id == uid){
                mainActivity.replaceFragment(PartyPromiseCreate())
            }else{
                Toast.makeText(requireContext(), "파티를 만든 사람만 이용가능합니다.", Toast.LENGTH_LONG).show()
            }
        }


        return root
    }

}
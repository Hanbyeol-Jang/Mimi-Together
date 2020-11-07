package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.Store

class StoreDetail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_storedetail, container, false)
        val mainActivity : MainActivity = activity as MainActivity


//        val bundle = arguments
//        val item : Store = bundle?.getSerializable("store_detail") as Store

        val btn01 : Button = root.findViewById(R.id.test_btn01)
        val btn02 : Button = root.findViewById(R.id.test_btn02)
        val btn03 : Button = root.findViewById(R.id.test_btn03)

//        val f = StoreInformationFragment()
//        f.arguments = bundle
        mainActivity.callFragment(StoreInformationFragment(), 0)

        btn01.setOnClickListener{
            mainActivity.callFragment(StoreInformationFragment(), 1)
        }
        btn02.setOnClickListener{
            mainActivity.callFragment(StoreMenuFragment(), 1)
        }
        btn03.setOnClickListener{
            mainActivity.callFragment(StoreMapFragment(), 1)
        }


        return root
    }


}
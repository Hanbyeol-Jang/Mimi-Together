package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.Store
import com.google.gson.Gson

class StoreInformationFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_storeinformation, container, false)
        val mainActivity : MainActivity = activity as MainActivity

        val store : Store = mainActivity.loadStore()

        val name : TextView = root.findViewById(R.id.info_name)
        val category : TextView = root.findViewById(R.id.info_category)
        val address : TextView = root.findViewById(R.id.info_address)
        val tel : TextView = root.findViewById(R.id.info_tel)

        name.text = store.name
        category.text = store.category
        address.text = store.address
        tel.text = store.tel

        return root
    }
}
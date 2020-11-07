package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import kotlinx.android.synthetic.main.fragment_partylist.*


class PartyCreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_partylist, container, false)
        val mainActivity : MainActivity = activity as MainActivity


        val btn : Button = root.findViewById(R.id.move_address_btn)

        btn.setOnClickListener {
            val ptName = input_party_name.text.toString()
            if(ptName == ""){
                Toast.makeText(mainActivity.applicationContext, "모임 이름을 적어주세요.", Toast.LENGTH_SHORT).show()
            }else{
                val f = PartyInputAddressFragment()
                val bundle = Bundle()
                bundle.putSerializable("partyName", ptName)
                f.arguments = bundle
                mainActivity.replaceFragment(f)
            }
        }

        return root
    }

}
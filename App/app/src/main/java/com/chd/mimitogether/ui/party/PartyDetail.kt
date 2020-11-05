package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.adapter.PartyMemberListAdapter
import com.chd.mimitogether.ui.party.dto.Party
import com.kakao.sdk.link.LinkClient

class PartyDetail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_partydetail, container, false)
        val mainActivity : MainActivity = activity as MainActivity

        val bundle = arguments
        val item : Party = bundle?.getSerializable("party_detail") as Party
        val name : TextView =  root.findViewById(R.id.party_deatil_name)
        name.text = item.ptName


        val adapter = PartyMemberListAdapter()
        val recyclerView : RecyclerView = root.findViewById(R.id.party_member_list_view)
        recyclerView?.adapter = adapter

        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        adapter.memberList.addAll(item.userList)
        adapter.notifyDataSetChanged()

        val share_btn : Button = root.findViewById(R.id.party_shared_btn)

        share_btn.setOnClickListener {
            val templateId = 39892L
            val templateArgs = HashMap<String, String>()
            Log.e("clickevent", item.id)
            templateArgs["partyId"] = item.id

            LinkClient.instance.customTemplate(requireContext(), templateId, templateArgs) { linkResult, error ->
                if (error != null) {
                    Log.e("myLog", "카카오링크 보내기 실패", error)
                }
                else if (linkResult != null) {
                    Log.d("myLog", "카카오링크 보내기 성공 ${linkResult.intent}")
                    startActivity(linkResult.intent)

                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.w("myLog", "Warning Msg: ${linkResult.warningMsg}")
                    Log.w("myLog", "Argument Msg: ${linkResult.argumentMsg}")
                }
            }

        }

        return root
    }

}
package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.adapter.MenuListAdapter
import com.chd.mimitogether.ui.party.dto.Menu
import com.chd.mimitogether.ui.party.dto.Store
import java.util.*

class StoreMenuFragment : Fragment() {

    var mlist = mutableListOf<Menu>()
    var alist = mutableListOf<Menu>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_storemenu, container, false)
        val mainActivity : MainActivity = activity as MainActivity

        val store : Store = mainActivity.loadStore()

        val mmadapter = MenuListAdapter()
        val mainmenuView : RecyclerView = root.findViewById(R.id.recyclerview_mainmenu)
        mainmenuView?.adapter = mmadapter

        mainmenuView?.layoutManager = LinearLayoutManager(requireContext())




        val amadapter = MenuListAdapter()
        val allmenuView : RecyclerView = root.findViewById(R.id.recyclerview_allmenu)
        allmenuView?.adapter = amadapter

        allmenuView?.layoutManager = LinearLayoutManager(requireContext())

        val st3 : StringTokenizer = StringTokenizer(store.menu, "//")
        while(st3.hasMoreTokens()){
            val st4 : StringTokenizer = StringTokenizer(st3.nextToken(),":")

            val name = st4.nextToken()
            var price : String? = null
            if(st4.hasMoreTokens()){
                price = st4.nextToken()
            }
            val menu = Menu(name, price)
            alist.add(menu)
        }



        amadapter.menuList.addAll(alist)
        amadapter.notifyDataSetChanged()

        mlist.add(alist[0])

        mmadapter.menuList.addAll(mlist)
        mmadapter.notifyDataSetChanged()

        return root
    }

}
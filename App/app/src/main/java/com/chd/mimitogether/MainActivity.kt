package com.chd.mimitogether

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.chd.mimitogether.ui.dashboard.DashboardFragment
import com.chd.mimitogether.ui.party.PartyListFragment
import com.chd.mimitogether.ui.notifications.NotificationsFragment
import com.chd.mimitogether.ui.party.PartyJoin
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val fragmentManager: FragmentManager = supportFragmentManager
    private val dashboardFragment: DashboardFragment = DashboardFragment()
    private val partyListFragment: PartyListFragment = PartyListFragment()
    private val notificationsFragment: NotificationsFragment = NotificationsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            Log.d("myLog", it.itemId.toString())
            when(it.itemId){
                R.id.navigation_party -> transaction.replace(R.id.frame_layout, partyListFragment)
                    .commitAllowingStateLoss()
                R.id.navigation_dashboard -> transaction.replace(
                    R.id.frame_layout,
                    dashboardFragment
                ).commitAllowingStateLoss()
                R.id.navigation_notifications -> transaction.replace(
                    R.id.frame_layout,
                    notificationsFragment
                ).commitAllowingStateLoss()
            }
            return@setOnNavigationItemSelectedListener true
        }

        val uri : Uri? = intent.data
        val party_id = uri?.getQueryParameter("partyId")
        if(party_id != null){
                val f = PartyJoin()
                val bundle = Bundle()
                bundle.putSerializable("partyId", party_id)
                f.arguments = bundle
                replaceFragment(f)
        }else{
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, PartyListFragment()).commitAllowingStateLoss()
        }





    }

    fun replaceFragment(fragment: Fragment){
        Log.e("mylog", "전환!")
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment).commit()
    }

    fun loadData(key: String) : String{
        val pref = getSharedPreferences("user", 0)
        if(key == "uid"){
            return pref.getLong(key, 0).toString()
        }else if(key == "uname"){
            return pref.getString(key, "")!!
        }else{
            return "Fail"
        }

    }



}


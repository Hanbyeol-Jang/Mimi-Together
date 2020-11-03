package com.chd.mimitogether

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.chd.mimitogether.ui.dashboard.DashboardFragment
import com.chd.mimitogether.ui.home.HomeFragment
import com.chd.mimitogether.ui.notifications.NotificationsFragment

class MainActivity : AppCompatActivity() {

    private val fragmentManager: FragmentManager = supportFragmentManager
    private val dashboardFragment: DashboardFragment = DashboardFragment()
    private val homeFragment: HomeFragment = HomeFragment()
    private val notificationsFragment: NotificationsFragment = NotificationsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            Log.d("myLog", it.itemId.toString())
            when(it.itemId){
                R.id.navigation_home -> transaction.replace(R.id.frame_layout, homeFragment).commitAllowingStateLoss()
                R.id.navigation_dashboard -> transaction.replace(R.id.frame_layout, dashboardFragment).commitAllowingStateLoss()
                R.id.navigation_notifications -> transaction.replace(R.id.frame_layout, notificationsFragment).commitAllowingStateLoss()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
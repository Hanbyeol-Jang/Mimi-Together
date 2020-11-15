package com.chd.mimitogether

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.findFragment
import com.chd.mimitogether.dto.UserRequest
import com.chd.mimitogether.ui.auction.CreateFragment
import com.chd.mimitogether.ui.auction.MyListFragment
import com.chd.mimitogether.ui.profile.ProfileFragment
import com.chd.mimitogether.ui.party.*
import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.dto.Store
import com.chd.mimitogether.ui.party.service.PartyService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.kakao.sdk.link.LinkClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.selects.select
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val fragmentManager: FragmentManager = supportFragmentManager
    private val partyListFragment: PartyListFragment = PartyListFragment()
    private val profileFragment: ProfileFragment = ProfileFragment()

    private val myListFragment: MyListFragment = MyListFragment()
    private val createFragment: CreateFragment = CreateFragment()
    private val currentFragment: Fragment? = null
    var selectParty: Party? = null

    lateinit var peopleItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            Log.d("myLog", it.itemId.toString())
            when (it.itemId) {
                R.id.navigation_party -> transaction.replace(R.id.frame_layout, partyListFragment)
                    .commitAllowingStateLoss()
                R.id.navigation_auction -> transaction.replace(R.id.frame_layout, createFragment)
//                R.id.navigation_dashboard -> transaction.replace(R.id.frame_layout, myListFragment)
                    .commitAllowingStateLoss()
                R.id.navigation_profile -> transaction.replace(R.id.frame_layout, profileFragment)
                    .commitAllowingStateLoss()
            }
            return@setOnNavigationItemSelectedListener true
        }

        val uri: Uri? = intent.data
        val party_id = uri?.getQueryParameter("partyId")
        if (party_id != null) {
            val retrofit =
                Retrofit.Builder().baseUrl(getString(R.string.base_url))
                    .addConverterFactory(
                        GsonConverterFactory.create()
                    ).build()
            val partyService = retrofit.create(PartyService::class.java)

            partyService.getPartyDetail(id = party_id).enqueue(object : Callback<Party> {
                override fun onResponse(call: Call<Party>, response: Response<Party>) {

                    selectParty = response.body()
                    Log.d("myLog", "$selectParty")
                    val uid = loadData("uid")

                    var flag = true
                    selectParty?.userList?.forEach { user ->
                        if (user.id == uid) {
                            replaceFragment(PartyDetail(), true)
                            flag = false
                        }
                    }

                    if (flag) {
                        val alertDialog: AlertDialog.Builder =
                            AlertDialog.Builder(this@MainActivity)
                        alertDialog.setTitle("모임 참여하기")
                            .setMessage(selectParty?.ptName + "모임에 참여하시겠습니까?")
                            .setPositiveButton("참여") { dialogInterface: DialogInterface, i: Int ->
                                partyService.joinParty(partyId = party_id, userId = uid)
                                    .enqueue(object : Callback<Party> {
                                        override fun onFailure(
                                            call: Call<Party>,
                                            t: Throwable
                                        ) {
                                            Toast.makeText( this@MainActivity, "서버가 불안정합니다.", Toast.LENGTH_SHORT).show()
                                            Log.i("userService", t.toString())
                                        }

                                        override fun onResponse(
                                            call: Call<Party>,
                                            response: Response<Party>
                                        ) {
                                            selectParty = response.body()!!

                                            replaceFragment(PartyDetail(), false)

                                        }
                                    })

                            }.setNegativeButton("취소") { dialogInterface: DialogInterface, i: Int ->
                                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
                                transaction.replace(R.id.frame_layout, PartyListFragment()).commitAllowingStateLoss()
                            }
                        val show: AlertDialog = alertDialog.show()
                    }

//                    for (i in 0 until selectParty!!.userList.size){
//                        if(party!!.userList[i].id == user_id){
//                            response_bundle.putSerializable("party_detail", party)
//                            fragment_party_detail.arguments = response_bundle
//
//                            mainActivity.replaceFragment(fragment_party_detail, false)
//
//                            break
//                        }
//                    }

                }

                override fun onFailure(call: Call<Party>, t: Throwable) {
                    Toast.makeText( this@MainActivity, "서버가 불안정합니다.", Toast.LENGTH_SHORT).show()
                    Log.i("JoinService", t.toString())
                }
            })

        } else {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, PartyListFragment()).commitAllowingStateLoss()
//            transaction.replace(R.id.frame_layout, PartyPromiseCreate()).commitAllowingStateLoss()
        }


    }

    fun replaceFragment(fragment: Fragment, backStackFlag: Boolean) {
        Log.e("mylog", "전환!")
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        if (backStackFlag)
            transaction.replace(R.id.frame_layout, fragment).addToBackStack(null).commit()
        else
            transaction.replace(R.id.frame_layout, fragment).commit()
    }

    fun addFragment(fragment: Fragment) {
        Log.e("mylog", "전환!")
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.add(R.id.frame_layout, fragment).addToBackStack(null).commit()
    }


    fun loadData(key: String): String {
        val pref = getSharedPreferences("user", 0)
        if (key == "uid") {
            return pref.getLong(key, 0).toString()
        } else if (key == "uname") {
            return pref.getString(key, "")!!
        } else {
            return "Fail"
        }

    }

    fun saveStore(store: Store) {
        val mPref: SharedPreferences = getPreferences(MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = mPref.edit()
        val gson: Gson = Gson()
        val json: String = gson.toJson(store)

        prefsEditor.putString("store", json)
        prefsEditor.commit()
    }

    fun loadStore(): Store {
        val mPref: SharedPreferences = getPreferences(MODE_PRIVATE)
        val gson: Gson = Gson()
        val json: String? = mPref.getString("store", "")
        return gson.fromJson(json, Store::class.java)
    }


    fun doLogout() {
        val pref = getSharedPreferences("user", 0)
        val edit = pref.edit()
        edit.clear()
        edit.commit()

        val intent = Intent(this, FirstActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun setToolbarTitle(title: String) {
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        val toolbar_text = toolbar.findViewById<TextView>(R.id.textView14)
        toolbar_text.text = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_people -> {
                Log.d("myLog", "MainActivity: action_people")

                val templateId = 39892L
                val templateArgs = HashMap<String, String>()
//                templateArgs["partyId"] = selectParty!!.id
                templateArgs["partyId"] = "5fafe0f53d6bbc048d9d4d71"

                val pref = getSharedPreferences("user", 0)
                templateArgs["userName"] = pref.getString("uname", "")!!
                templateArgs["partyName"] = selectParty!!.ptName

                LinkClient.instance.customTemplate(
                    this,
                    templateId,
                    templateArgs
                ) { linkResult, error ->
                    if (error != null) {
                        Log.e("myLog", "카카오링크 보내기 실패", error)
                    } else if (linkResult != null) {
                        Log.d("myLog", "카카오링크 보내기 성공 ${linkResult.intent}")
                        startActivity(linkResult.intent)

                        // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                        Log.w("myLog", "Warning Msg: ${linkResult.warningMsg}")
                        Log.w("myLog", "Argument Msg: ${linkResult.argumentMsg}")
                    }
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        peopleItem = menu?.findItem(R.id.action_people)!!
        peopleItem.isVisible = false
        return true
//        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        Log.d("myLog", "MainActivity: onBackPressed")
        Log.d("myLog", "MainActivity: ${fragmentManager.fragments}")

        val currentf = fragmentManager.findFragmentById(R.id.frame_layout)
        currentf?.let {
            fragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }

        Log.d("myLog", "MainActivity: ${fragmentManager.fragments}")
        super.onBackPressed()

    }
}


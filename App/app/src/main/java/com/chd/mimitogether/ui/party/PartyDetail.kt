package com.chd.mimitogether.ui.party

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.Glide
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.adapter.StoreGridListAdapter
import com.chd.mimitogether.ui.party.dto.MultiStore
import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.dto.Store
import com.chd.mimitogether.ui.party.dto.StorePageDto
import com.chd.mimitogether.ui.party.service.StoreService
import com.kakao.sdk.link.LinkClient
import kotlinx.android.synthetic.main.activity_survey.*
import kotlinx.android.synthetic.main.item_storegrid.view.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class PartyDetail : Fragment() {

    var pageno = 1
    var shared_btn: ImageButton? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_partydetail, container, false)
        val mainActivity: MainActivity = activity as MainActivity

        val bundle = arguments
        val item: Party = bundle?.getSerializable("party_detail") as Party
//        val name: TextView = root.findViewById(R.id.party_detail_name)
        val location: TextView = root.findViewById(R.id.partydetail_location)
//        val membercount: TextView = root.findViewById(R.id.partydetail_member_count)
//        val backbtn: ImageButton = root.findViewById(R.id.partydetail_backbtn)
        val scrollview: NestedScrollView = root.findViewById(R.id.partydetail_scrollview)
        val promise_time: TextView = root.findViewById(R.id.promise_time)
        val promise_location: TextView = root.findViewById(R.id.promise_location)

        mainActivity.setToolbarTitle("${item.ptName} (${item.userList.size}명)")
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainActivity.peopleItem.isVisible = true

        location.text = item.promiseLocation
        if (item.promiseTime != null) {
            promise_time.text = item.promiseTime.toString()
        }
        if (item.promiseStore != null) {
            promise_location.text = item.promiseStore.name
        }

        val adapter = StoreGridListAdapter()
        val gridview: RecyclerView = root.findViewById(R.id.store_grid)

        gridview.adapter = adapter
        gridview.layoutManager = GridLayoutManager(requireContext(), 3)

        val animator: RecyclerView.ItemAnimator? = gridview.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }

        val retrofit =
            Retrofit.Builder().baseUrl(getString(R.string.base_url))
                .addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
        val storeService = retrofit.create(StoreService::class.java)

        val progressbar : ProgressBar = root.findViewById(R.id.progressBar)
        progressbar.visibility = View.VISIBLE

        storeService.getRecommandStoreList(PartyId = mainActivity.selectParty!!.id).enqueue(object : Callback<List<MultiStore>>{
            override fun onResponse(
                call: Call<List<MultiStore>>,
                response: Response<List<MultiStore>>
            ) {
                Log.e("multistore", response.body().toString())
                val multiStoreList = response.body() as List<MultiStore>
                val storeList = mutableListOf<Store>()
                multiStoreList.forEach{multiStore ->
                    val store = Store(
                        id = multiStore.rid,
                        boID = "null",
                        name = multiStore.name,
                        address = multiStore.address,
                        tel = multiStore.tel,
                        category = multiStore.category,
                        mainMn = multiStore.mainMn,
                        price = multiStore.price,
                        menu = multiStore.menu,
                        rating = (multiStore.rating * 10).roundToInt() / 10.0,
                        rvwCnt = 0,
                        img = multiStore.img,
                        tags = multiStore.tags
                    )
                    storeList.add(store)
                }
                progressbar.visibility = View.GONE
                adapter.storeList.addAll(storeList)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<MultiStore>>, t: Throwable) {
                Log.e("storeService", t.toString())
            }
//            override fun onResponse(call: Call<MultiStore>, response: Response<MultiStore>) {
//                Log.e("multistore", response.body().toString())
//                adapter.storeList.addAll(response.body()?.content as List<Store>)
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onFailure(call: Call<MultiStore>, t: Throwable) {
//                Log.e("storeService", t.toString())
//            }

        })

//        storeService.getStoreList(pageno = 0)
//            .enqueue(object : Callback<StorePageDto> {
//                override fun onFailure(
//                    call: Call<StorePageDto>,
//                    t: Throwable
//                ) {
//                    Log.i("storeService", t.toString())
//                }
//
//                override fun onResponse(
//                    call: Call<StorePageDto>,
//                    response: Response<StorePageDto>
//                ) {
//                    adapter.storeList.addAll(response.body()?.content as List<Store>)
//                    adapter.notifyDataSetChanged()
//                }
//            })

//        scrollview.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            if (v.getChildAt(v.childCount - 1) != null) {
//                if ((scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
//                    scrollY > oldScrollY
//                ) {
//
//                    storeService.getStoreList(pageno = pageno)
//                        .enqueue(object : Callback<StorePageDto> {
//                            override fun onFailure(
//                                call: Call<StorePageDto>,
//                                t: Throwable
//                            ) {
//                                Log.i("storeService", t.toString())
//                            }
//
//                            override fun onResponse(
//                                call: Call<StorePageDto>,
//                                response: Response<StorePageDto>
//                            ) {
//                                Log.e("pageload", "here")
//                                adapter.storeList.addAll(response.body()?.content as List<Store>)
//                                adapter.notifyDataSetChanged()
//                                pageno++
//                            }
//                        })
//                }
//            }
//        }))

        adapter.setItemClickListener(object : StoreGridListAdapter.ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val store = adapter.storeList[position]

                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())

                val dialogView = layoutInflater.inflate(R.layout.store_dialog, null)
                alertDialog.setView(dialogView)
                val show: AlertDialog = alertDialog.show()

                val image: ImageView = dialogView.findViewById(R.id.store_dialog_image)
                val name: TextView = dialogView.findViewById(R.id.store_dialog_name)
                val rating: TextView = dialogView.findViewById(R.id.store_dialog_rating)
                val btn: Button = dialogView.findViewById(R.id.store_dialog_btn)

                Glide.with(dialogView.context).load(store?.img).into(image)
                name.text = store.name
                rating.text = store.rating.toString() + " / 5.0"


                btn.setOnClickListener {
                    show.dismiss()
                    mainActivity.saveStore(adapter.storeList[position])
//                    mainActivity.replaceFragment(StoreDetail())
                    mainActivity.addFragment(StoreDetail())
                }

            }
        })

//        shared_btn = mainActivity.findViewById(R.id.shared_btn)
//        shared_btn!!.visibility = View.VISIBLE

//        shared_btn!!.setOnClickListener {
//            val templateId = 39892L
//            val templateArgs = HashMap<String, String>()
//            templateArgs["partyId"] = item.id
//            Log.e("partyId", item.id)
//
//            LinkClient.instance.customTemplate(
//                requireContext(),
//                templateId,
//                templateArgs
//            ) { linkResult, error ->
//                if (error != null) {
//                    Log.e("myLog", "카카오링크 보내기 실패", error)
//                } else if (linkResult != null) {
//                    Log.d("myLog", "카카오링크 보내기 성공 ${linkResult.intent}")
//                    startActivity(linkResult.intent)
//
//                    // 카카오링크 보내기에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
//                    Log.w("myLog", "Warning Msg: ${linkResult.warningMsg}")
//                    Log.w("myLog", "Argument Msg: ${linkResult.argumentMsg}")
//                }
//            }
//        }

//        val adapter = PartyMemberListAdapter()
//        val recyclerView : RecyclerView = root.findViewById(R.id.party_member_list_view)
//        recyclerView?.adapter = adapter
//
//        recyclerView?.layoutManager = GridLayoutManager(requireContext(),3)
//
//        adapter.memberList.addAll(item.userList)
//        adapter.notifyDataSetChanged()
//
//        val share_btn : Button = root.findViewById(R.id.party_shared_btn)
//


//        backbtn.setOnClickListener {
//            mainActivity.replaceFragment(PartyListFragment())
//        }
        return root
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        shared_btn!!.visibility = View.INVISIBLE
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()

        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mainActivity.peopleItem.isVisible = false
    }

}
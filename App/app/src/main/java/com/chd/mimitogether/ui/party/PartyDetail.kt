package com.chd.mimitogether.ui.party

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
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
import com.chd.mimitogether.ui.party.service.StoreService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class PartyDetail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_partydetail, container, false)
        val mainActivity: MainActivity = activity as MainActivity

        val item: Party = mainActivity.selectParty!!
        val location: TextView = root.findViewById(R.id.partydetail_location)
        val promiseTime: TextView = root.findViewById(R.id.promise_time)
        val promiseLocation: TextView = root.findViewById(R.id.promise_location)

        mainActivity.setToolbarTitle("${item.ptName} (${item.userList.size}명)")
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainActivity.peopleItem.isVisible = true

        location.text = item.promiseLocation
        if (item.promiseTime != null) {
            promiseTime.text = item.promiseTime
        }
        if (item.promiseStore != null) {
            promiseLocation.text = item.promiseStore.name
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

        val progressbar: ProgressBar = root.findViewById(R.id.progressBar)
        val storeNotFound : LinearLayout = root.findViewById(R.id.store_not_found)
        progressbar.visibility = View.VISIBLE
        storeNotFound.visibility = View.GONE
        storeService.getRecommandStoreList(PartyId = item.id)
            .enqueue(object : Callback<List<MultiStore>> {
                override fun onResponse(
                    call: Call<List<MultiStore>>,
                    response: Response<List<MultiStore>>
                ) {
                    Log.e("multistore", response.body().toString())
                    val multiStoreList = response.body() as List<MultiStore>
                    val storeList = mutableListOf<Store>()
                    multiStoreList.forEach { multiStore ->
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
                    if(storeList.isEmpty()){
                        storeNotFound.visibility = View.VISIBLE
                    }

                    progressbar.visibility = View.GONE
                    adapter.storeList.addAll(storeList)
                    adapter.notifyDataSetChanged()


                }

                override fun onFailure(call: Call<List<MultiStore>>, t: Throwable) {
                    Toast.makeText(requireContext(), "서버가 불안정합니다.", Toast.LENGTH_SHORT).show()
                }
            })

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
                rating.text = "★ " + store.rating.toString() + " / 5.0"


                btn.setOnClickListener {
                    show.dismiss()
                    mainActivity.saveStore(adapter.storeList[position])
                    mainActivity.addFragment(StoreDetail())
                }

            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mainActivity.peopleItem.isVisible = false
    }
}
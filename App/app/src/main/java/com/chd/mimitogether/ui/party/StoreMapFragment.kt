package com.chd.mimitogether.ui.party

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chd.mimitogether.MainActivity
import com.chd.mimitogether.R
import com.chd.mimitogether.ui.party.dto.geocoding.GeocodingResponse
import com.chd.mimitogether.ui.party.service.GeocodingService
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.fragment_storemap.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StoreMapFragment : Fragment(), OnMapReadyCallback {

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("myLog", "etstetset")
        val root = inflater.inflate(R.layout.fragment_storemap, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        Log.d("myLog", "onResume!!!!!!!!")

        mainActivity = activity as MainActivity

        val naverMap = childFragmentManager.findFragmentById(R.id.naver_map) as MapFragment
        naverMap.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        val retrofit = Retrofit.Builder().baseUrl("https://naveropenapi.apigw.ntruss.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val geocodingService = retrofit.create(GeocodingService::class.java)

        val currentStore = mainActivity.loadStore()

        geocodingService.getLatLng(query = currentStore.address).enqueue(object : Callback<GeocodingResponse> {
            override fun onResponse(
                call: Call<GeocodingResponse>,
                response: Response<GeocodingResponse>
            ) {
                val lat = response.body()!!.addresses[0].y.toDouble()
                val lng = response.body()!!.addresses[0].x.toDouble()
                val latlng = LatLng(lat, lng)

                naverMap.cameraPosition = CameraPosition(latlng, 18.0)

                val marker = Marker()
                marker.position = latlng
                marker.map = naverMap
            }

            override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
                Log.e("myLog", t.toString())
            }
        })
    }

}
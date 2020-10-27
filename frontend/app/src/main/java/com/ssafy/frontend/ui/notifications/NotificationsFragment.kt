package com.ssafy.frontend.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ssafy.frontend.R
import com.ssafy.frontend.Store
import com.ssafy.frontend.TestService
import com.ssafy.frontend.User
import kotlinx.android.synthetic.main.fragment_notifications.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onStart() {
        super.onStart()

        val retrofit = Retrofit.Builder().baseUrl("http://192.168.28.33:9999/").addConverterFactory(GsonConverterFactory.create()).build()

        val testService = retrofit.create(TestService::class.java)

        noti_btn.setOnClickListener {

            val name = noti_name.text.toString()
            val content = noti_content.text.toString()
            val id = noti_id.text.toString()
            val store = Store(id,name,content)

            val call = testService.input(store)

            call.enqueue(object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("mylog", t.toString())
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Log.d("mylog","보내는데 성공했어요!!")
                }
            })
        }
    }
}
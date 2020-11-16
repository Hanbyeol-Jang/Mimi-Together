package com.chd.mimitogether.ui.auction

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.chd.mimitogether.R

class CreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.auction_fragment_create, container, false)

        val prevButton = root.findViewById<Button>(R.id.navi_button1)
        val nextButton = root.findViewById<Button>(R.id.navi_button2)


        prevButton.setOnClickListener {
            Log.d("myLog", "CreateFragment: prevButton")
        }

        nextButton.setOnClickListener {
            Log.d("myLog", "CreateFragment: nextButton")
        }

        val menuListener = View.OnClickListener {
            val button = root.findViewById<Button>(it.id)
            Log.d("myLog", "CreateFragment: ${it.id - R.id.menu_button1}")
        }

        val menuButton1 = root.findViewById<Button>(R.id.menu_button1)
        val menuButton2 = root.findViewById<Button>(R.id.menu_button2)
        val menuButton3 = root.findViewById<Button>(R.id.menu_button3)
        val menuButton4 = root.findViewById<Button>(R.id.menu_button4)
        val menuButton5 = root.findViewById<Button>(R.id.menu_button5)
        val menuButton6 = root.findViewById<Button>(R.id.menu_button6)
        val menuButton7 = root.findViewById<Button>(R.id.menu_button7)
        val menuButton8 = root.findViewById<Button>(R.id.menu_button8)

        menuButton1.setOnClickListener(menuListener)
        menuButton2.setOnClickListener(menuListener)
        menuButton3.setOnClickListener(menuListener)
        menuButton4.setOnClickListener(menuListener)
        menuButton5.setOnClickListener(menuListener)
        menuButton6.setOnClickListener(menuListener)
        menuButton7.setOnClickListener(menuListener)
        menuButton8.setOnClickListener(menuListener)

        return root
    }

}
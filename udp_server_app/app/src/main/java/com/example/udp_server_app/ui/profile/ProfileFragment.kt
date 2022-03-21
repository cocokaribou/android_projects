package com.example.udp_server_app.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.udp_server_app.Logger
import com.example.udp_server_app.ui.profile.ProfileFragment.Companion.numArray
import com.example.udp_server_app.R
import com.example.udp_server_app.ui.MainActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var backPressedCallback: OnBackPressedCallback
    lateinit var pageChangeCallback: ViewPager2.OnPageChangeCallback

    private var vp1: ViewPager2? = null
    private var vp2: ViewPager2? = null
    private var vp3: ViewPager2? = null

    private var btnSave: Button? = null

    companion object {
        val numArray = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // back pressed
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).removeFrag(this@ProfileFragment)
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(backPressedCallback)

        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Logger("scrolled to $position")
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Logger("$position selected")
            }
        }

        initUI()
    }

    private fun initUI() {
        activity?.let {
            vp1 = it.findViewById(R.id.vp_1)
            vp2 = it.findViewById(R.id.vp_2)
            vp3 = it.findViewById(R.id.vp_3)
            btnSave = it.findViewById(R.id.btn_save_profile)
        }

        vp1?.let {
            it.adapter = ViewPagerAdapter(requireContext())
            it.registerOnPageChangeCallback(pageChangeCallback)
        }

        vp2?.let {
            it.adapter = ViewPagerAdapter(requireContext())
            it.registerOnPageChangeCallback(pageChangeCallback)
        }
        vp3?.let {
            it.adapter = ViewPagerAdapter(requireContext())
            it.registerOnPageChangeCallback(pageChangeCallback)
        }

        btnSave?.let {
            //TODO save view as an image
            it.setOnClickListener {
                Toast.makeText(requireContext(), getString(R.string.profile_saved), Toast.LENGTH_LONG).show()
                (activity as MainActivity).removeFrag(this)
            }
        }
    }

}

class ViewPagerAdapter(private val mContext: Context) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_profile1, parent, false)
        return ViewPagerHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.tv.text = position.toString()
    }

    override fun getItemCount() = numArray.size


    class ViewPagerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.tv_num)
    }

}
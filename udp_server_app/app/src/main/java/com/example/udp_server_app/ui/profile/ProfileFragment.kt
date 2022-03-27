package com.example.udp_server_app.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
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
    private var vp1: ViewPager2? = null
    private var vp2: ViewPager2? = null
    private var vp3: ViewPager2? = null
    private var btnSave: Button? = null
    private var etName: EditText? = null

    companion object {
        val numArray = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    }

    // back pressed callback
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            (activity as MainActivity).removeFrag(this@ProfileFragment)
        }
    }

    // viewpager listener
    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
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

    // editText key listener
    private val editTextWatcher = object:View.OnKeyListener {
        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                val inputMethodManager: InputMethodManager? = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                inputMethodManager?.hideSoftInputFromWindow(view!!.windowToken, 0)

                etName?.isFocusable = false
                return true
            }
            return false
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(backPressedCallback)

        initUI()
    }

    private fun initUI() {
        activity?.let {
            vp1 = it.findViewById(R.id.vp_1)
            vp2 = it.findViewById(R.id.vp_2)
            vp3 = it.findViewById(R.id.vp_3)
            btnSave = it.findViewById(R.id.btn_save_profile)
            etName = it.findViewById(R.id.et_name)
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

        etName?.setOnKeyListener(editTextWatcher)

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
package com.example.shared_viewmodel.ui.list

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shared_viewmodel.LogHelper
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.FragmentListBinding
import com.example.shared_viewmodel.model.MainData
import com.example.shared_viewmodel.model.StoreData
import com.example.shared_viewmodel.ui.StoreSharedViewModel

class ListFragment : Fragment() {

    private var binding: FragmentListBinding? = null

    private val storeSharedViewModel: StoreSharedViewModel by activityViewModels()

    private val _adapter: ModulesAdapter by lazy { ModulesAdapter() }

    private val mainList = mutableListOf<MainData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentBinding = FragmentListBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeSharedViewModel.initPageCount()

        binding?.apply {
            viewModel = storeSharedViewModel
            lifecycleOwner = viewLifecycleOwner
            listFragment = this@ListFragment

            rv.adapter = _adapter
            rv.layoutManager = LinearLayoutManager(requireContext())

            ivRefresh.setOnClickListener {
                hideKeyboard()
                processData(et.text.toString())

                val pagingCount = if (etSetCount.text.isEmpty()) 0 else etSetCount.text.toString().toInt()
                _adapter.setPageCount(pagingCount)
                _adapter.differ.submitList(null)
                storeSharedViewModel.setMainList(mainList.toList())
            }

            et.apply {
                setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                        // scroll top
                        rv.scrollToPosition(0)
                        hideKeyboard()

                        processData(et.text.toString())
                        storeSharedViewModel.setMainList(mainList.toList())

                        return@setOnKeyListener true
                    }
                    false
                }
            }
            etSetCount.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    hideKeyboard()
                    _adapter.setPageCount(etSetCount.text.toString().toInt())
                    return@setOnKeyListener true
                }
                false
            }
            _adapter.setOnItemClickListener(clickListener)

            // observe store list
            storeSharedViewModel.mainList.observe(viewLifecycleOwner) { list ->
                _adapter.differ.submitList(list)
            }
        }
    }

    private fun processData(input: String) {
        mainList.clear()

        val inputList = input.split(" ")
        val storeList = mutableListOf<StoreData>()
        inputList.forEachIndexed { i, item ->
            val storeData = StoreData(i, item)
            storeList.add(storeData)
            mainList.add(MainData(ModulesType.Vertical, data = storeData))
        }
        mainList.add(MainData(ModulesType.Grid, dataList = storeList))
        mainList.add(MainData(ModulesType.Horizontal, dataList = storeList))
    }

    private val clickListener : (String) -> Unit = { item ->
        storeSharedViewModel.setStoContent(item)
        goDetailFrag()
    }

    private fun goDetailFrag() {
        findNavController().navigate(R.id.action_listFragment_to_detailFragment)
    }

    private fun hideKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        activity?.currentFocus?.let { view ->
            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}


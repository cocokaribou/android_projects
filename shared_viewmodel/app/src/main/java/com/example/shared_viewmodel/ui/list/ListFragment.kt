package com.example.shared_viewmodel.ui.list

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shared_viewmodel.MainActivity
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

    private val callback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.action_listFragment_to_homeFragment)
        }
    }

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
                _adapter.differ.submitList(null)
                refreshMain()
            }
            et.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    refreshMain()
                    return@setOnKeyListener true
                }
                false
            }
            etSetCount.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    refreshMain()
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

    private fun refreshMain() {
        // hide keyboard
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        activity?.currentFocus?.let { view ->
            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
        }
        binding?.let {
            // scroll to top
            it.rv.scrollToPosition(0)
            // process input string
            processData(it.et.text.toString())
            // reset adapter page count
            val pagingCount = if (it.etSetCount.text.isEmpty()) 4 else it.etSetCount.text.toString().toInt()
            _adapter.setPagingCount(pagingCount)
        }
        // submit data list
        storeSharedViewModel.setMainList(mainList.toList())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as? MainActivity)?.onBackPressedDispatcher?.addCallback(callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}


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
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.FragmentListBinding
import com.example.shared_viewmodel.ui.StoreSharedViewModel

class ListFragment : Fragment() {

    private var binding: FragmentListBinding? = null

    private val storeSharedViewModel: StoreSharedViewModel by activityViewModels()

    private val _adapter: ModulesAdapter by lazy { ModulesAdapter() }

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

            et.apply {
                setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                        // hide keyboard
                        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        activity?.currentFocus?.let { view ->
                            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
                        }

                        // set livedata
                        val inputString = et.text.toString()
                        storeSharedViewModel.setStoList(inputString.split(" ").toTypedArray())

                        return@setOnKeyListener true
                    }
                    false
                }
            }

            // observe store list
            storeSharedViewModel.stoList.observe(viewLifecycleOwner) { list ->
                _adapter.differ.submitList(list.toList())
            }
        }

        val clickListener : (String) -> Unit = { item ->
            storeSharedViewModel.setStoContent(item)
            goDetailFrag()
        }
        _adapter.setOnItemClickListener(clickListener)
    }

    fun goDetailFrag() {
        findNavController().navigate(R.id.action_listFragment_to_detailFragment)
    }
}


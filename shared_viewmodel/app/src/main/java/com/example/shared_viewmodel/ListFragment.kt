package com.example.shared_viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shared_viewmodel.databinding.FragmentListBinding
import com.example.shared_viewmodel.databinding.ItemListBinding
import com.example.shared_viewmodel.model.BaseViewModel
import com.example.shared_viewmodel.model.SubViewModel

class ListFragment : Fragment() {

    private var binding: FragmentListBinding? = null

    private val sharedViewModel: BaseViewModel by activityViewModels()
    private val subViewModel: SubViewModel by viewModels()

    private val _adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentBinding = FragmentListBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            viewModel = sharedViewModel
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
                        sharedViewModel.setStoList(inputString.split(" ").toTypedArray())

                        return@setOnKeyListener true
                    }
                    false
                }
            }

            // observe store list
            sharedViewModel.stoList.observe(viewLifecycleOwner) { list ->
                _adapter.differ.submitList(list.toList())
            }
        }

        val clickListener : (String) -> Unit = { item ->
            sharedViewModel.setStoContent(item)
            goDetailFrag()
        }
        _adapter.setOnItemClickListener(clickListener)
    }

    fun goDetailFrag() {
        findNavController().navigate(R.id.action_listFragment_to_detailFragment)
    }
}


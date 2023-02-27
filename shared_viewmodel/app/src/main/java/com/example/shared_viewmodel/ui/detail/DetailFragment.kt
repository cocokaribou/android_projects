//package com.example.shared_viewmodel.ui.detail
//
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.activity.OnBackPressedCallback
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.activityViewModels
//import androidx.navigation.fragment.findNavController
//import com.example.shared_viewmodel.ui.MainActivity
//import com.example.shared_viewmodel.R
//import com.example.shared_viewmodel.databinding.FragmentDetailBinding
//import com.example.shared_viewmodel.ui.StoreSharedViewModel
//
//class DetailFragment : Fragment() {
//
//    private val storeSharedViewModel: StoreSharedViewModel by activityViewModels()
//
//    private var binding: FragmentDetailBinding? = null
//
//    private lateinit var callback: OnBackPressedCallback
//
//    private var detailFragmentManager: FragmentManager? = null
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val fragmentBinding = FragmentDetailBinding.inflate(inflater, container, false)
//        binding = fragmentBinding
//
//        detailFragmentManager = (requireActivity() as? MainActivity)?.supportFragmentManager
//
//        return fragmentBinding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding?.apply {
//            var content = ""
//            var pageCount = 0
//
//            storeSharedViewModel.stoContent.observe(viewLifecycleOwner) { _content ->
//                content = _content
//                receivedString = content + pageCount
//            }
//
//            storeSharedViewModel.currentStack.observe(viewLifecycleOwner) { _pageCount ->
//                pageCount = _pageCount
//                receivedString = content + pageCount
//            }
//
//            btnClose.setOnClickListener {
//
//                findNavController().navigate(R.id.action_detailFragment_to_listFragment)
//            }
//
//            tvTitle.setOnLongClickListener { v ->
//                v.visibility = View.GONE
//                et.visibility = View.VISIBLE
//                et.requestFocus()
//                true
//            }
//
//            btnStackFrag.setOnClickListener {
////                findNavController().navigate(R.id.action_detailFragment_to_detailFragment)
//
//                storeSharedViewModel.incStackCount()
//
//                /**
//                 * separate fragment manager
//                 * */
//                if (detailFragmentManager == null) return@setOnClickListener
//
//                detailFragmentManager!!.beginTransaction().apply {
//                    add(DetailFragment(), "detailfrag$pageCount")
//                    commitNow()
//                }
//            }
//        }
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                storeSharedViewModel.currentStack.observe(viewLifecycleOwner) { page ->
//                    if (page == 0) {
//                        findNavController().navigate(R.id.action_detailFragment_to_listFragment)
//                    } else {
//                        if (detailFragmentManager == null) return@observe
//
//                        detailFragmentManager!!.beginTransaction().apply {
//                            remove(this@DetailFragment)
//                            commitNow()
//                        }
//                    }
//                }
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        callback.remove()
//
//        storeSharedViewModel.decStackCount()
//    }
//}
package com.example.shared_viewmodel.ui.webView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.FragmentWebviewBinding
import com.example.shared_viewmodel.ui.MainActivity
import com.example.shared_viewmodel.ui.MainViewModel
import com.example.shared_viewmodel.ui.home.HomeFragmentDirections
import com.example.shared_viewmodel.ui.leftmenu.LeftMenuFragment
import com.example.shared_viewmodel.util.Logger
import java.util.*

class WebViewFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentWebviewBinding? = null

    private val activityViewModel: MainViewModel by activityViewModels()

    private lateinit var callback: OnBackPressedCallback
    private var navHostFrag: FragmentManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWebviewBinding.inflate(inflater)

        navHostFrag = (requireActivity() as? MainActivity)?.supportFragmentManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navHostFrag ?: return

        binding.home.setOnClickListener {
            findNavController().navigate(R.id.action_webView_to_home, Bundle().apply { putString("result", "webview") })
            navHostFrag!!.fragments.filterIsInstance<WebViewFragment>().forEach {
                navHostFrag!!.beginTransaction()
                    .remove(it)
                    .commitNow()
            }
        }

        binding.title.text = "webview${activityViewModel.webFragmentCount}"

        // nav controller 로 stack (불가!)
//        binding.stack.setOnClickListener {
//            if (activityViewModel.webFragmentCount >= 5) {
//                findNavController().navigate(R.id.action_webView_pop, Bundle().apply {
//                    putInt("count", activityViewModel.webFragmentCount++)
//                })
//            } else {
//                findNavController().navigate(R.id.action_webView_stack, Bundle().apply {
//                    putInt("count", activityViewModel.webFragmentCount++)
//                })
//            }
//        }

        // support fragment manager로 stack
        binding.stack.setOnClickListener {
            navHostFrag!!.beginTransaction()
                .add(R.id.nav_host_fragment, WebViewFragment(), "tag${activityViewModel.webFragmentCount++}")
                .commitNow()

            if (activityViewModel.webFragmentCount > 6) {
                val first = navHostFrag!!.fragments.filterIsInstance<WebViewFragment>().first()
                navHostFrag!!.beginTransaction()
                    .remove(first)
                    .commitNow()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navHostFrag ?: return

                activityViewModel.webFragmentCount =
                    if (activityViewModel.webFragmentCount > 0) activityViewModel.webFragmentCount - 1
                    else 0

                val webFrags = navHostFrag!!.fragments.filterIsInstance<WebViewFragment>()
                if (webFrags.size == 1) findNavController().navigate(R.id.action_webView_to_home, Bundle().apply { putString("result", "webview") })

                if (webFrags.isNotEmpty())
                    navHostFrag!!.beginTransaction()
                        .remove(webFrags.last())
                        .commitNow()
                else findNavController().navigate(R.id.action_webView_to_home, Bundle().apply { putString("result", "webview") })
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        callback.remove()
    }
}
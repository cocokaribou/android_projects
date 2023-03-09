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
import androidx.navigation.fragment.findNavController
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.FragmentWebviewBinding
import com.example.shared_viewmodel.ui.MainActivity
import com.example.shared_viewmodel.ui.MainViewModel
import com.example.shared_viewmodel.util.Logger
import com.example.shared_viewmodel.util.PopUpUtil.toast

class WebViewFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentWebviewBinding? = null

    private val activityViewModel: MainViewModel by activityViewModels()

    private lateinit var callback: OnBackPressedCallback
    private var fragManager: FragmentManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWebviewBinding.inflate(inflater)

        fragManager = (requireActivity() as? MainActivity)?.supportFragmentManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragManager ?: return

        initUI()
    }

    private fun initUI() = with(binding) {
        home.setOnClickListener {
            findNavController().navigateUp()
            fragManager!!.fragments.filterIsInstance<WebViewFragment>().forEach {
                fragManager!!.beginTransaction()
                    .remove(it)
                    .commitNow()
            }
        }

        title.text = "webview${activityViewModel.webFragmentCount}"

        // support fragment manager로 stack
        stack.setOnClickListener {
            fragManager!!.beginTransaction()
                .add(R.id.nav_host_fragment, WebViewFragment(), "tag${activityViewModel.webFragmentCount++}")
                .commitNow()

            if (activityViewModel.webFragmentCount > 6) {
                val first = fragManager!!.fragments.filterIsInstance<WebViewFragment>().first()
                fragManager!!.beginTransaction()
                    .remove(first)
                    .commitNow()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                fragManager ?: return

                activityViewModel.webFragmentCount =
                    if (activityViewModel.webFragmentCount > 0) activityViewModel.webFragmentCount - 1
                    else 0
                activityViewModel.webFragArgument.postValue(activityViewModel.webFragmentCount)

                val webFrags = fragManager!!.fragments.filterIsInstance<WebViewFragment>()
                if (webFrags.size == 1) findNavController().navigateUp()

                if (webFrags.isNotEmpty())
                    fragManager!!.beginTransaction()
                        .remove(webFrags.last())
                        .commitNow()
                else findNavController().navigateUp()

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
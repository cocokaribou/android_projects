package com.example.shared_viewmodel.ui.webView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.FragmentWebviewBinding
import com.example.shared_viewmodel.ui.MainViewModel
import com.example.shared_viewmodel.util.Logger

var count = 0
class WebViewFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentWebviewBinding? = null

    private val activityViewModel :MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWebviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = "webview${arguments?.get("count")}"

        binding.home.setOnClickListener {
            count = 0
            findNavController().navigate(R.id.action_webView_to_home)
        }

        binding.stack.setOnClickListener {
            findNavController().navigate(R.id.webViewFragment, Bundle().apply {
                putInt("count", count++)
            })

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
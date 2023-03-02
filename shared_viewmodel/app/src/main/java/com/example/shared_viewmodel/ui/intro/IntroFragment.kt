package com.example.shared_viewmodel.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.shared_viewmodel.R
import com.example.shared_viewmodel.databinding.FragmentIntroBinding
import com.example.shared_viewmodel.util.Logger
import kotlinx.coroutines.*

class IntroFragment : Fragment() {
    private val binding get() = _binding!!
    private var _binding: FragmentIntroBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentIntroBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            findNavController().navigate(R.id.homeFragment, Bundle().apply {
                putString("test", "test")
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
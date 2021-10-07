package com.cocokaribou.recycler_view_expandable_item.ui.dragview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cocokaribou.recycler_view_expandable_item.databinding.FragmentFrameTopExoplayerBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class TopExoFrag : Fragment() {
    lateinit var binding: FragmentFrameTopExoplayerBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFrameTopExoplayerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPlayer()
        super.onViewCreated(view, savedInstanceState)
    }

    fun initPlayer() {
        var player: SimpleExoPlayer? = SimpleExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                binding.playerview.player = exoPlayer
                val mediaItem = MediaItem.fromUri("https://youtu.be/LKqttZ4bX7Q")
                exoPlayer.setMediaItem(mediaItem)
            }
        player?.run {
            playWhenReady = true
            release()
        }
        player = null
    }
}
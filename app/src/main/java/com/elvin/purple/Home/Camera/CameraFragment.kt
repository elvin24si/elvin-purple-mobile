package com.elvin.purple.Home.Camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elvin.purple.databinding.FragmentCameraBinding
import com.google.android.material.tabs.TabLayoutMediator

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup ViewPager2 & TabLayout
        val adapter = CameraTabsAdapter(this)
        binding.viewPagerCamera.adapter = adapter

        val tabTitles = arrayOf("Camera Capture", "Camera Scan", "QR Code")
        TabLayoutMediator(binding.tabLayoutCamera, binding.viewPagerCamera) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

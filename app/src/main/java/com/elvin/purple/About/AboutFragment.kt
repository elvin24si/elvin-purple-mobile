package com.elvin.purple.About

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.elvin.purple.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarAbout.title = "Tentang Bina Desa"

        // Set up the Adapter for ViewPager2
        val adapter = AboutPagerAdapter(this)
        binding.viewPagerAbout.adapter = adapter

        // Bind TabLayout and ViewPager2 together
        TabLayoutMediator(binding.tabLayoutAbout, binding.viewPagerAbout) { tab, position ->
            tab.text = when (position) {
                0 -> "Menu Utama"
                1 -> "Info Tambahan"
                2 -> "Dokumen"
                else -> null
            }
        }.attach()
    }

    // Inner ViewPager Adapter Class
    private class AboutPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AboutMenuFragment()
                1 -> AboutExtraFragment()
                2 -> LegalDocumentFragment()
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.elvin.purple.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.elvin.purple.LoginActivity
import com.elvin.purple.R
import com.elvin.purple.data.api.NewsApiClient // Pastikan package API Client Anda benar
import com.elvin.purple.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil berita pertama kali saat fragment dimuat
        fetchBeritaTerkini()

        // Tombol Refresh Berita dari XML
        binding.btnRefreshNews.setOnClickListener {
            fetchBeritaTerkini()
        }

        // 1. Rumus Button
        binding.btnRumus.setOnClickListener {
            val intent = Intent(requireContext(), RumusActivity::class.java)
            intent.putExtra("judul", "Rumus Bangun Ruang")
            intent.putExtra("desc", "Hitung luas dan volume untuk dokumen fisik.")
            startActivity(intent)
        }

        // 2. Welcome Button
        binding.btnWelcome.setOnClickListener {
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            intent.putExtra("judul", "Welcome Screen")
            intent.putExtra("desc", "Selamat datang di sistem informasi Bina Desa.")
            startActivity(intent)
        }

        // 3. Web View Button
        binding.btnWebView.setOnClickListener {
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", "https://elvin24pcr.alwaysdata.net/")
            intent.putExtra("title", "Portal Web Bina Desa")
            startActivity(intent)
        }

        // 4. Logout Logic
        binding.btnLogout.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            sharedPref.edit().putBoolean("isLogin", false).apply()

            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        // Chip Filter Logic
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChipId = checkedIds.firstOrNull()
            if (selectedChipId != null) {
                val chip = group.findViewById<Chip>(selectedChipId)
                Toast.makeText(requireContext(), "Filter: ${chip.text}", Toast.LENGTH_SHORT).show()

                when (selectedChipId) {
                    R.id.chipAll -> {
                        binding.btnRumus.visibility = View.VISIBLE
                        binding.btnWelcome.visibility = View.VISIBLE
                        binding.btnWebView.visibility = View.VISIBLE
                    }
                    R.id.chipWeb -> {
                        binding.btnRumus.visibility = View.GONE
                        binding.btnWelcome.visibility = View.GONE
                        binding.btnWebView.visibility = View.VISIBLE
                    }
                    R.id.chipBonus -> {
                        binding.btnRumus.visibility = View.VISIBLE
                        binding.btnWelcome.visibility = View.VISIBLE
                        binding.btnWebView.visibility = View.GONE
                    }
                }
            }
        }
    }

    // Fungsi Pengambilan REST API Berita ala Modul Kampus
    private fun fetchBeritaTerkini() {
        binding.tvNewsTitle.text = "Memuat berita..."
        binding.tvNewsDesc.text = ""

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = NewsApiClient.apiService.getNews()

                if (response.status == "OK" && response.data.isNotEmpty()) {
                    val beritaUtama = response.data[0]

                    binding.tvNewsTitle.text = beritaUtama.title
                    binding.tvNewsDesc.text = beritaUtama.description ?: "Tidak ada deskripsi untuk berita ini."
                } else {
                    binding.tvNewsTitle.text = "Format data API tidak sesuai atau data kosong."
                }
            } catch (e: Exception) {
                binding.tvNewsTitle.text = "Gagal memuat berita."
                binding.tvNewsDesc.text = "Terjadi gangguan parsing data."
                android.util.Log.e("API_ERROR", "Detail Error: ${e.localizedMessage}", e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
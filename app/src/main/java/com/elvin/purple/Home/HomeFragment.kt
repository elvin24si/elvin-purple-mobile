package com.elvin.purple.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elvin.purple.LoginActivity
import com.elvin.purple.R
import com.elvin.purple.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
            intent.putExtra("url", "https://elvin24pcr.alwaysdata.net/") // Placeholder
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
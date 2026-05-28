package com.elvin.purple.About

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.elvin.purple.R
import com.elvin.purple.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the internal custom toolbar title via View Binding
        binding.toolbarAbout.title = "Tentang Bina Desa"

        // 1. Define the structural content array for the ListView items
        val menuItems = arrayOf(
            "Definisi Program",
            "Fitur Utama",
            "Kebijakan Privasi (Privacy Policy)",
            "Versi Aplikasi"
        )

        // 2. Instantiate the ArrayAdapter using Android's built-in simple list layout layout
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            menuItems
        )

        // 3. Attach the adapter to the ListView
        binding.listViewAbout.adapter = adapter

        // 4. Handle row item interactions
        binding.listViewAbout.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> showInfoDialog(
                    "Definisi",
                    "Ini adalah program yang mempermudah pemrosesan, penyimpanan, dan pengaksesan produk hukum dan dokumen publik desa."
                )
                1 -> showInfoDialog(
                    "Fitur Utama",
                    "• List Dokumen\n• Menambah dokumen\n• Edit Dokumen\n• Hapus Dokumen"
                )
                2 -> showInfoDialog(
                    "Privacy Policy",
                    "Data pengguna program Bina Desa sepenuhnya dienkripsi secara lokal untuk melindungi kerahasiaan dokumen publik desa."
                )
                3 -> Toast.makeText(requireContext(), "Bina Desa - v1.0.0 (Beta)", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Helper method to display row-details inside a stylized alert dialog window
    private fun showInfoDialog(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Tutup", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
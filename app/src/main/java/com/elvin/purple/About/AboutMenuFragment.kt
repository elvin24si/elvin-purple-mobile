package com.elvin.purple.About

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.elvin.purple.databinding.FragmentAboutMenuBinding

class AboutMenuFragment : Fragment() {

    private var _binding: FragmentAboutMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuItems = arrayOf(
            "Definisi Program", "Fitur Utama", "Kebijakan Privasi (Privacy Policy)", "Versi Aplikasi"
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, menuItems)
        binding.listViewAbout.adapter = adapter

        binding.listViewAbout.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> showInfoDialog("Definisi", "Ini adalah program yang mempermudah pemrosesan...")
                1 -> showInfoDialog("Fitur Utama", "• List Dokumen\n• Menambah dokumen...")
                2 -> showInfoDialog("Privacy Policy", "Data pengguna program Bina Desa sepenuhnya...")
                3 -> Toast.makeText(requireContext(), "Bina Desa - v1.0.0 (Beta)", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
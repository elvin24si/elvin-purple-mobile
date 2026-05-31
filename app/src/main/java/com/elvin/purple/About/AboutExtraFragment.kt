package com.elvin.purple.About

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.elvin.purple.databinding.FragmentAboutExtraBinding

class AboutExtraFragment : Fragment() {

    private var _binding: FragmentAboutExtraBinding? = null
    private val binding get() = _binding!!

    private val contributorList = listOf(
        Contributor("Elvin", "Lead Android Developer", "elvin24si@pcr.ac.id"),
        Contributor("Contributor A", "UI/UX Designer", "contributor.a@desa.go.id"),
        Contributor("Contributor B", "Backend Engineer", "contributor.b@desa.go.id"),
        Contributor("Contributor C", "Quality Assurance", "contributor.c@desa.go.id"),
        Contributor("Contributor D", "Data Analyst", "contributor.d@desa.go.id"),
        Contributor("Contributor E", "Technical Writer", "contributor.e@desa.go.id"),
        Contributor("Contributor F", "Project Manager", "contributor.f@desa.go.id"),
        Contributor("Contributor G", "System Administrator", "contributor.g@desa.go.id"),
        Contributor("Contributor H", "Mobile Developer Partner", "contributor.h@desa.go.id"),
        Contributor("Contributor I", "Supervisor", "contributor.i@desa.go.id")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutExtraBinding.inflate(inflater, container, false)
        return binding.root
    }

    // 2. Inisialisasi Adapter dan LayoutManager di onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi adapter dengan data list dan handle click listener
        val adapter = ContributorAdapter(contributorList)

        binding.recyclerViewExtra.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
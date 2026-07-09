package com.elvin.purple.About

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.elvin.purple.BaseActivity
import com.elvin.purple.data.local.AppDatabase
import com.elvin.purple.data.local.LegalDocumentDao
import com.elvin.purple.databinding.FragmentLegalDocumentBinding

import com.elvin.purple.utils.PermissionHelper
import com.elvin.purple.utils.NotificationHelper
import com.elvin.purple.utils.ReminderHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class LegalDocumentFragment : Fragment() {

    private var _binding: FragmentLegalDocumentBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: AppDatabase
    private lateinit var dao: LegalDocumentDao
    private lateinit var adapter: LegalDocumentAdapter

    // Register launcher untuk request permission sesuai struktur PermissionHelper
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            executeNotificationWorkflow()
        } else {
            Toast.makeText(requireContext(), "Izin notifikasi ditolak. Tidak bisa menerima update.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLegalDocumentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi database Room
        database = AppDatabase.getDatabase(requireContext())
        dao = database.legalDocumentDao()

        // Inisialisasi adapter dengan list kosong terlebih dahulu
        adapter = LegalDocumentAdapter(emptyList()) { document ->
            handleRequestAccess()
        }

        binding.recyclerViewDocument.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@LegalDocumentFragment.adapter
        }

        // Ambil data dari Room secara asinkron
        loadDocuments()

        // Handle tambah dokumen baru via FAB
        binding.fabAddDocument.setOnClickListener {
            showAddDocumentDialog()
        }
    }

    private fun loadDocuments() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            var list = dao.getAllDocuments()
            
            // Jika database kosong, lakukan seeding data awal
            if (list.isEmpty()) {
                val seedData = listOf(
                    LegalDocument(title = "UU Hak Cipta Digital", date = "12 Feb 2026", status = "Terbatas", description = "Dokumen mengenai regulasi hak cipta konten di platform digital."),
                    LegalDocument(title = "NDAs Vendor Aplikasi", date = "05 Mar 2026", status = "Rahasia", description = "Perjanjian kerahasiaan antara pihak pengembang dan pihak ketiga."),
                    LegalDocument(title = "Kebijakan Privasi Pengguna v2.1", date = "20 Mei 2026", status = "Publik", description = "Draf pembaruan kebijakan penanganan data privasi pengguna aplikasi.")
                )
                dao.insertDocuments(seedData)
                list = dao.getAllDocuments()
            }

            withContext(Dispatchers.Main) {
                adapter.updateData(list)
            }
        }
    }

    private fun showAddDocumentDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Tambah Dokumen Hukum")

        // Buat form input programmatically
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 20)
        }

        val etTitle = EditText(requireContext()).apply {
            hint = "Judul Dokumen"
        }
        val etStatus = EditText(requireContext()).apply {
            hint = "Status (misal: Publik, Rahasia)"
        }
        val etDesc = EditText(requireContext()).apply {
            hint = "Deskripsi Dokumen"
        }

        layout.addView(etTitle)
        layout.addView(etStatus)
        layout.addView(etDesc)
        builder.setView(layout)

        builder.setPositiveButton("Simpan") { dialog, _ ->
            val title = etTitle.text.toString().trim()
            val status = etStatus.text.toString().trim()
            val desc = etDesc.text.toString().trim()

            if (title.isNotEmpty() && status.isNotEmpty() && desc.isNotEmpty()) {
                val today = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault()).format(java.util.Date())
                val newDocument = LegalDocument(
                    title = title,
                    date = today,
                    status = status,
                    description = desc
                )

                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    dao.insertDocument(newDocument)
                    val updatedList = dao.getAllDocuments()
                    withContext(Dispatchers.Main) {
                        adapter.updateData(updatedList)
                        Toast.makeText(requireContext(), "Dokumen berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun handleRequestAccess() {
        // Cek apakah perangkat memerlukan izin POST_NOTIFICATIONS (Android 13+)
        if (PermissionHelper.isNotificationPermissionRequired()) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (PermissionHelper.hasPermission(requireContext(), permission)) {
                executeNotificationWorkflow()
            } else {
                // Gunakan requestPermission dari Helper-mu
                PermissionHelper.requestPermission(requestPermissionLauncher, permission)
            }
        } else {
            // Android 12 ke bawah tidak butuh runtime permission notifikasi
            executeNotificationWorkflow()
        }
    }

    private fun executeNotificationWorkflow() {
        // 1. Munculkan Notifikasi Pertama langsung via Object NotificationHelper
        val dummyIntent = android.content.Intent(requireContext(), BaseActivity::class.java)
        NotificationHelper.showNotification(
            context = requireContext(),
            title = "Akses Diminta",
            message = "Mohon tunggu, kami akan memberi notifikasi setelah sudah (10 detik)",
            intent = dummyIntent
        )

        // 3. Set Reminder selama 10 detik menggunakan fungsi setReminderInSeconds
        ReminderHelper.setReminderInSeconds(
            context = requireContext(),
            seconds = 10,
            title = "Akses Diberikan",
            message = "Akses dokumen hukum telah disetujui. Ketuk untuk melihat.",
            targetActivity = BaseActivity::class.java
        )

        Toast.makeText(requireContext(), "Permintaan diproses (Tunggu 10 detik)", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
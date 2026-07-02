package com.elvin.purple.About

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.elvin.purple.BaseActivity
import com.elvin.purple.databinding.FragmentLegalDocumentBinding
import com.elvin.purple.model.LegalDocument
import com.elvin.purple.utils.PermissionHelper
import com.elvin.purple.utils.NotificationHelper
import com.elvin.purple.utils.ReminderHelper
import java.util.Calendar

class LegalDocumentFragment : Fragment() {

    private var _binding: FragmentLegalDocumentBinding? = null
    private val binding get() = _binding!!

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

    private val documentList = listOf(
        LegalDocument("1", "UU Hak Cipta Digital", "12 Februari 2026", "Terbatas", "Dokumen mengenai regulasi hak cipta konten di platform digital."),
        LegalDocument("2", "NDAs Vendor Aplikasi", "05 Maret 2026", "Rahasia", "Perjanjian kerahasiaan antara pihak pengembang dan pihak ketiga."),
        LegalDocument("3", "Kebijakan Privasi Pengguna v2.1", "20 Mei 2026", "Publik", "Draf pembaruan kebijakan penanganan data privasi pengguna aplikasi.")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLegalDocumentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LegalDocumentAdapter(documentList) { document ->
            handleRequestAccess()
        }

        binding.recyclerViewDocument.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
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

        // 2. Hitung waktu 10 detik ke depan untuk dimasukkan ke setReminder milikmu
        val calendar = Calendar.getInstance().apply {
            add(Calendar.SECOND, 10)
        }
        val targetHour = calendar.get(Calendar.HOUR_OF_DAY)
        val targetMinute = calendar.get(Calendar.MINUTE)

        // 3. Set Reminder menggunakan fungsi asli dari ReminderHelper-mu
        ReminderHelper.setReminder(
            context = requireContext(),
            hour = targetHour,
            minute = targetMinute,
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
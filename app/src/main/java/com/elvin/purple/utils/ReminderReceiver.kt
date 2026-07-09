package com.elvin.purple.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.elvin.purple.BaseActivity // Sesuaikan dengan package BaseActivity kamu

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Akses Diberikan"
        val message = intent.getStringExtra("message") ?: "Akses dokumen hukum telah disetujui."

        val notificationIntent = Intent(context, BaseActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("NAVIGATE_TO", "SUCCESS_FRAGMENT")
        }

        NotificationHelper.showNotification(
            context = context,
            title = title,
            message = message,
            intent = notificationIntent
        )
    }
}
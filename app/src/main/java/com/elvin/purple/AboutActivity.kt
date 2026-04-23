package com.elvin.purple

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)

        val judul = intent.getStringExtra("judul")
        val deskripsi = intent.getStringExtra("desc")

        findViewById<TextView>(R.id.tvJudul).text = judul
        findViewById<TextView>(R.id.tvDeskripsi).text = deskripsi
    }
}
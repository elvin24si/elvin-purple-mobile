package com.elvin.purple

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elvin.purple.databinding.ActivityLoginBinding // Pastikan nama file XML Anda activity_login.xml

class LoginActivity : AppCompatActivity() {

    // Menggunakan ViewBinding agar pemanggilan ID lebih aman dan bersih
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inisialisasi View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        // 2. Mengatur padding sistem bars (status bar & navigasi)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Welcome, $username!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
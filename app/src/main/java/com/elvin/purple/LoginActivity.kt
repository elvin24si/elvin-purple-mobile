package com.elvin.purple

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elvin.purple.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inisialisasi View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 2. Logika Tombol Login dengan fitur AuthActivity
        binding.btnLogin.setOnClickListener {
            // Mengambil input menggunakan ViewBinding
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()

            // Validasi: Username harus sama dengan Password (sesuai logika AuthActivity Anda)
            if (username == password && username.isNotEmpty()) {

                // Fitur: Simpan status login ke SharedPreferences
                val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                // Navigasi ke MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                Toast.makeText(this, "Welcome, $username!", Toast.LENGTH_SHORT).show()
                finish() // Menutup LoginActivity agar tidak bisa kembali dengan tombol back
            } else {
                // Fitur: Tampilkan Dialog Error dari AuthActivity
                showErrorDialog()
            }
        }
    }

    // Fungsi Dialog dari AuthActivity
    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle("Login Gagal")
            .setMessage("Username dan Password harus sama dan tidak boleh kosong.")
            .setPositiveButton("OK", null)
            .show()
    }
}
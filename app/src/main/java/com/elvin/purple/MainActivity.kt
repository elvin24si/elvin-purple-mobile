package com.elvin.purple

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.elvin.purple.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRumus.setOnClickListener {
            val intent = Intent(this, RumusActivity::class.java)
            intent.putExtra("judul", "Rumus Bangun Ruang")
            intent.putExtra("desc", "Hitung volume dan luas segitiga dan balok di sini.")
            startActivity(intent)
        }

        binding.btnWelcome.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.putExtra("judul", "Welcome Screen")
            intent.putExtra("desc", "Selamat datang di ekosistem WhiteFrame Labs.")
            startActivity(intent)
        }

        binding.btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            intent.putExtra("judul", "About Us")
            intent.putExtra("desc", "Misi kami memperbaiki standar PC Indonesia.")
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi")
            builder.setMessage("Yakin ingin Logout?")

            builder.setPositiveButton("Iya") { _, _ ->
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

            builder.setNegativeButton("Tidak") { _, _ ->
                Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
            }
            builder.show()
        }
    }
}
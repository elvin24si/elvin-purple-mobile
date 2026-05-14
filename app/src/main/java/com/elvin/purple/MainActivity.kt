package com.elvin.purple

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.elvin.purple.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import android.content.Context

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
            intent.putExtra("desc", "Selamat datang ke App Bina Desa.")
            startActivity(intent)
        }

        binding.btnWebView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", "https://elvin24pcr.alwaysdata.net/")
            intent.putExtra("judul", "Web View Bina Desa")
            startActivity(intent)
        }

        binding.btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            intent.putExtra("judul", "About Us")
            intent.putExtra("desc", "Kami App pengurusan produk hukum dan dokumen publik.")
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi")
            builder.setMessage("Yakin ingin Logout?")

            builder.setPositiveButton("Iya") { _, _ ->
                val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()

                editor.putBoolean("isLogin", false)
                editor.apply()

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
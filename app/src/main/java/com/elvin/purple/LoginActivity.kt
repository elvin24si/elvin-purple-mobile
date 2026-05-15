package com.elvin.purple

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elvin.purple.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- LOGIKA LOGIN ---
        binding.btnLogin.setOnClickListener {
            val usernameInput = binding.editUsername.text.toString()
            val passwordInput = binding.editPassword.text.toString()

            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val savedUser = sharedPref.getString("reg_username", "")
            val savedPass = sharedPref.getString("reg_password", "")

            val rulePraktikum = (usernameInput == passwordInput && usernameInput.isNotEmpty())
            val ruleSharedPref = (usernameInput == savedUser && passwordInput == savedPass && usernameInput.isNotEmpty())

            if (rulePraktikum || ruleSharedPref) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", usernameInput)
                editor.apply()

                startActivity(Intent(this, BaseActivity::class.java))
                Toast.makeText(this, "Selamat Datang $usernameInput!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                binding.editUsername.error = "Username atau Password salah"
                binding.editPassword.error = "Periksa kembali kredensial Anda"
            }
        }

        // --- NAVIGASI KE REGISTER ---
        binding.txtToRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}
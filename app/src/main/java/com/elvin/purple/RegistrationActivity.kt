package com.elvin.purple

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elvin.purple.databinding.ActivityRegistrationBinding
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupDropdownAgama()
        setupDatePicker()

        binding.btnRegister.setOnClickListener {
            validateAndRegister()
        }
    }

    private fun setupDropdownAgama() {
        val listAgama = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Konghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listAgama)
        binding.regAgama.setAdapter(adapter)
    }

    private fun setupDatePicker() {
        binding.regTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.regTanggalLahir.setText(date)
            }, year, month, day)
            datePicker.show()
        }
    }

    private fun validateAndRegister() {
        val nama = binding.regNama.text.toString()
        val tglLahir = binding.regTanggalLahir.text.toString()
        val username = binding.regUsername.text.toString()
        val password = binding.regPassword.text.toString()
        val confirmPass = binding.regConfirmPassword.text.toString()
        val agama = binding.regAgama.text.toString()
        var isValid = true

        val genderId = binding.regGenderGroup.checkedRadioButtonId
        if (genderId == -1) {
            Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (nama.isEmpty()) { binding.regNama.error = "Nama tidak boleh kosong"; isValid = false }
        if (tglLahir.isEmpty()) { binding.regTanggalLahir.error = "Pilih tanggal lahir"; isValid = false }
        if (agama.isEmpty()) { binding.regAgama.error = "Pilih agama"; isValid = false }
        if (username.isEmpty()) { binding.regUsername.error = "Username tidak boleh kosong"; isValid = false }
        if (password.isEmpty()) { binding.regPassword.error = "Password tidak boleh kosong"; isValid = false }
        if (confirmPass != password) { binding.regConfirmPassword.error = "Password tidak cocok"; isValid = false }

        if (isValid) {
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("reg_nama", nama)
            editor.putString("reg_tgl_lahir", tglLahir)
            editor.putString("reg_agama", agama)
            editor.putString("reg_username", username)
            editor.putString("reg_password", password)

            val genderText = if (genderId == binding.rbLaki.id) "Laki-laki" else "Perempuan"
            editor.putString("reg_gender", genderText)
            editor.apply()

            Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
            finish() // Return to LoginActivity
        }
    }
}
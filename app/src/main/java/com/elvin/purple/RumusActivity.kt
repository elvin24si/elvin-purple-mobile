package com.elvin.purple

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class RumusActivity : AppCompatActivity() {

    private val TAG = "RumusActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rumus)
        val judul = intent.getStringExtra("judul")
        val deskripsi = intent.getStringExtra("desc")
        findViewById<TextView>(R.id.tvJudul).text = judul
        findViewById<TextView>(R.id.tvDeskripsi).text = deskripsi

        val etAlas   = findViewById<EditText>(R.id.etAlasSegitiga)
        val etTinggi = findViewById<EditText>(R.id.etTinggiSegitiga)
        val btnSeg   = findViewById<Button>(R.id.btnHitungSegitiga)
        val tvHasilSeg = findViewById<TextView>(R.id.tvHasilSegitiga)

        btnSeg.setOnClickListener {
            Log.e(TAG, "Mulai Perhitungan Bangun Datar Segitiga")
            val alas   = etAlas.text.toString().toDoubleOrNull()
            val tinggi = etTinggi.text.toString().toDoubleOrNull()
            if (alas == null || tinggi == null) {
                tvHasilSeg.text = "Hasil: input tidak valid"
                return@setOnClickListener
            }
            val luas = 0.5 * alas * tinggi
            tvHasilSeg.text = "Hasil: $luas cm²"
        }

        val etPanjang = findViewById<EditText>(R.id.etPanjangBalok)
        val etLebar   = findViewById<EditText>(R.id.etLebarBalok)
        val etTinggiB = findViewById<EditText>(R.id.etTinggiBalok)
        val btnBalok  = findViewById<Button>(R.id.btnHitungBalok)
        val tvHasilBalok = findViewById<TextView>(R.id.tvHasilBalok)

        btnBalok.setOnClickListener {
            Log.e(TAG, "Mulai Perhitungan Bangun Ruang Balok")
            val p = etPanjang.text.toString().toDoubleOrNull()
            val l = etLebar.text.toString().toDoubleOrNull()
            val t = etTinggiB.text.toString().toDoubleOrNull()
            if (p == null || l == null || t == null) {
                tvHasilBalok.text = "Hasil: input tidak valid"
                return@setOnClickListener
            }
            val volume = p * l * t
            tvHasilBalok.text = "Hasil: $volume cm³"
        }
    }
}
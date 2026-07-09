package com.elvin.purple

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.elvin.purple.About.AboutFragment
import com.elvin.purple.About.RequestSuccessFragment
import com.elvin.purple.Home.HomeFragment
import com.elvin.purple.Home.Camera.CameraFragment
import com.elvin.purple.Profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Cek dulu apakah Activity ini dibuka dari klik notifikasi sukses
        val isNavigatedFromNotification = handleNotificationIntent(intent)

        // Jika BUKAN dari notifikasi, baru set default fragment ke HomeFragment
        if (!isNavigatedFromNotification) {
            replaceFragment(HomeFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_about -> replaceFragment(AboutFragment())
                R.id.nav_profile -> replaceFragment(ProfileFragment())
                R.id.nav_camera -> replaceFragment(CameraFragment())
            }
            true
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Handle jika aplikasi sedang terbuka/di background lalu notifikasi diklik
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent?): Boolean {
        val destination = intent?.getStringExtra("NAVIGATE_TO")
        if (destination == "SUCCESS_FRAGMENT") {
            // Mengarah ke RequestSuccessFragment menggunakan ID container-mu (R.id.fragmentContainer)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RequestSuccessFragment())
                .addToBackStack(null)
                .commit()
            return true // Mengembalikan true jika sukses navigasi
        }
        return false
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
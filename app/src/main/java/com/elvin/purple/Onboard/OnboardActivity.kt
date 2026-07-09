package com.elvin.purple.Onboard

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.elvin.purple.R
import com.elvin.purple.databinding.ActivityMainBinding
import com.elvin.purple.databinding.ActivityOnboardBinding

class OnboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentsList = listOf(OnboardAFragment(), OnboardBFragment(), OnboardCFragment())
        val adapter = OnboardFragmentAdapter(this, fragmentsList)
        binding.OnboardViewPager2.adapter = adapter

        binding.dotIndicator.attachTo(binding.OnboardViewPager2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
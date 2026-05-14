package com.elvin.purple

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val toolbar: Toolbar = findViewById(R.id.toolbarWebView)
        setSupportActionBar(toolbar)

        val judul = intent.getStringExtra("judul")
        val deskripsi = intent.getStringExtra("desc")

        supportActionBar?.title = judul
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // 2. Setup WebView
        val webView: WebView = findViewById(R.id.webView)
        val url = intent.getStringExtra("url") ?: "https://www.google.com"

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        // Force links to open inside the app instead of a browser
        webView.webViewClient = WebViewClient()

        webView.loadUrl(url)
    }

    // 3. Handle the Toolbar Back Button Click
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Navigates back to MainActivity
        return true
    }
}
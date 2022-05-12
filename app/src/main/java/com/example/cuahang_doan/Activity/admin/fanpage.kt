package com.example.cuahang_doan.Activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.os.Bundle
import android.view.View
import com.example.cuahang_doan.R
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar

class fanpage : AppCompatActivity() {
    private var toolbar_Fanpage: Toolbar? = null
    private var webviewFanpage: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fanpage)
        setactionbar()
        setwebview()
    }

    private fun setwebview() {
        webviewFanpage = findViewById(R.id.webviewFanpage)
        webviewFanpage?.loadUrl("https://www.facebook.com/profile.php?id=100007219005457")
        webviewFanpage?.setWebViewClient(WebViewClient())
    }

    private fun setactionbar() {
        toolbar_Fanpage = findViewById(R.id.toolbar_Fanpage)
        setSupportActionBar(toolbar_Fanpage)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        toolbar_Fanpage?.setNavigationIcon(R.drawable.back)
        toolbar_Fanpage?.setNavigationOnClickListener(View.OnClickListener { finish() })
    }
}
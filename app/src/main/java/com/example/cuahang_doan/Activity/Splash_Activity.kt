package com.example.cuahang_doan.Activity

import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout
import android.os.Bundle
import com.example.cuahang_doan.R
import android.view.animation.Animation
import android.content.Intent
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.cuahang_doan.Activity.MainActivity

class Splash_Activity : AppCompatActivity() {
    private var splashRelivelayout: RelativeLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_)
        splashRelivelayout = findViewById(R.id.relativeSplash)
        val animation = AnimationUtils.loadAnimation(this@Splash_Activity, R.anim.anim_splash)
        splashRelivelayout?.setAnimation(animation)
        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }, 4000)
    }
}
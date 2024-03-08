package com.verianggoro.tmdbmovie.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.verianggoro.tmdbmovie.R
import com.verianggoro.tmdbmovie.databinding.ActivitySplashScreenBinding
import java.util.Timer
import java.util.TimerTask

class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                goMain()
            }
        }, 2000)
    }

    private fun goMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
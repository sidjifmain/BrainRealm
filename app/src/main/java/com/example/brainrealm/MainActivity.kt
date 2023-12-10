package com.example.brainrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.brainrealm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load Animation
        val rocketAnimation = AnimationUtils.loadAnimation(this, R.anim.rocket_animation)

        // Set Animation Listener
        rocketAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                // Animasyon başladığında yapılacak işlemler (isteğe bağlı)
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Animasyon bittiğinde yapılacak işlemler

                startLoginActivity()
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Animasyon tekrarlandığında yapılacak işlemler (isteğe bağlı)
            }
        })

        // Start Animation
        binding.splashScreenRocket.startAnimation(rocketAnimation)
    }

    private fun startLoginActivity() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
}
package com.example.brainrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brainrealm.databinding.ActivitySelectGameModeBinding

class SelectGameMode : AppCompatActivity() {
    lateinit var binding : ActivitySelectGameModeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectGameModeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.selectGameModeFerdi.setOnClickListener {
            startActivity(
                Intent(
                    this , Themes::class.java
                )
            )
        }

        binding.selectGameModeDuel.setOnClickListener {
            startActivity(
                Intent(
                    this , Duel1::class.java
                )
            )
        }


    }
}
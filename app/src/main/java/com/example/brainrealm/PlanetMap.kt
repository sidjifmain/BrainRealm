package com.example.brainrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.brainrealm.databinding.ActivityPlanetMapBinding

class PlanetMap : AppCompatActivity() {
    lateinit var binding : ActivityPlanetMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.planetMapSettingsButton.setOnClickListener {
            startActivity(
                Intent(
                    this@PlanetMap , Settings::class.java
                )
            )
        }
    }
}
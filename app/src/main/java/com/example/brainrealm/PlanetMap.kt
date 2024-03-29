package com.example.brainrealm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.brainrealm.Models.UserModel
import com.example.brainrealm.Models.UserViewModel
import com.example.brainrealm.databinding.ActivityPlanetMapBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class PlanetMap : AppCompatActivity() {

    lateinit var binding: ActivityPlanetMapBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid.orEmpty()

        loadImageFromFirebaseStorage(userId, binding.planetMapUserImage)


        // ViewModelProvider kullanımı
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)



        userViewModel.getUserData().observe(this, { userData ->
            userData?.let {
                updateUI(it)
            }
        })





        binding.planetMapLeaderbordBtn.setOnClickListener {
            startActivity(
                Intent(
                    this , LeaderPage::class.java
                )
            )
        }

        binding.planetMapCoinIcon.setOnClickListener {
            startActivity(
                Intent(
                    this , BuyCoin::class.java
                )
            )
        }

        binding.planet1.setOnClickListener {
            val intent = Intent(this, Question::class.java)
            intent.putExtra("planet", "planet1")
            startActivity(intent)
        }

        binding.planetMapUserImage.setOnClickListener{
            startActivity(
                Intent(this , Settings::class.java)
            )
        }

        binding.planet2.setOnClickListener {
            val intent = Intent(this, Question::class.java)
            intent.putExtra("planet", "planet2")
            startActivity(intent)
        }

        binding.planet3.setOnClickListener {
            val intent = Intent(this, Question::class.java)
            intent.putExtra("planet", "planet3")
            startActivity(intent)
        }

        binding.planet4.setOnClickListener {
            val intent = Intent(this, Question::class.java)
            intent.putExtra("planet", "planet4")
            startActivity(intent)
        }

        binding.planet5.setOnClickListener {
            val intent = Intent(this, Question::class.java)
            intent.putExtra("planet", "planet5")
            startActivity(intent)
        }

        binding.planet6.setOnClickListener {
            val intent = Intent(this, Question::class.java)
            intent.putExtra("planet", "planet6")
            startActivity(intent)
        }

        binding.notificationIcon.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    Notifications::class.java
                )
            )
        }



    }





    private fun updateUI(userModel: UserModel) {
        binding.planetMapCoin.text = userModel.coin.toString()
        Log.d("salam",userModel.fullName.toString())
        binding.planetMapFullName.text = userModel.fullName.toString()
    }

    fun loadImageFromFirebaseStorage(userId: String?, imageView: ImageView) {
        // Firebase Storage referansını al
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        // Kullanıcının UID'sini al
        val currentUserId = userId ?: FirebaseAuth.getInstance().currentUser?.uid

        // Kullanıcının profil resminin bulunduğu yol
        val imagePath = "user_images/$currentUserId.jpg"

        // Storage'de bulunan resmin referansını al
        val imageRef = storageRef.child(imagePath)

        // Resmin URL'sini al
        imageRef.downloadUrl
            .addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                // imageUrl'i kullanarak ImageView'e resmi yükle (örneğin Picasso kütüphanesi kullanılabilir)
                // Not: Picasso kullanmak için build.gradle dosyanıza şu bağımlılığı ekleyin: implementation 'com.squareup.picasso:picasso:2.71828'
                Picasso.get().load(imageUrl).into(imageView)
            }
            .addOnFailureListener { exception ->
                // Hata durumunda işlemleri burada yönetebilirsiniz
                Log.e("FirebaseStorage", "Error getting image URL: $exception")
            }
    }

    override fun onResume() {
        super.onResume()

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)



        userViewModel.getUserData().observe(this, { userData ->
            userData?.let {
                updateUI(it)
            }
        })

    }
}

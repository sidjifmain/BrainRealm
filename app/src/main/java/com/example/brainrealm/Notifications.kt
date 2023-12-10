package com.example.brainrealm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.brainrealm.Adapters.LeaderListAdapter
import com.example.brainrealm.Models.UserModel
import com.example.brainrealm.Models.UserViewModel
import com.example.brainrealm.databinding.ActivityNotificationsBinding
import com.example.brainrealm.databinding.ActivityQuestionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class Notifications : AppCompatActivity() {
    lateinit var binding : ActivityNotificationsBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid.orEmpty()

        userViewModel.getUserData().observe(this, { userData ->
            userData?.let {
                updateUI(it)
            }
        })

        loadImageFromFirebaseStorage(userId.toString() , binding.notificationsUseImage)
    }

    private fun updateUI(userModel: UserModel) {
        binding.notificationsCoin.text = userModel.coin.toString()
        Log.d("salam",userModel.fullName.toString())
        binding.notificationsFullName.text = userModel.fullName.toString()
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
}
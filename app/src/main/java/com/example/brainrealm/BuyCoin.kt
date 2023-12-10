package com.example.brainrealm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.brainrealm.Models.UserModel
import com.example.brainrealm.Models.UserViewModel
import com.example.brainrealm.databinding.ActivityBuyCoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class BuyCoin : AppCompatActivity() {

    lateinit var binding : ActivityBuyCoinBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid.orEmpty()


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.buyCoinPopulyar.setOnClickListener {
            increaseCoinValueInFirestore()
        }

        userViewModel.getUserData().observe(this, { userData ->
            userData?.let {
                updateUI(it)
            }
        })

        loadImageFromFirebaseStorage(userId, binding.buyCoinUserImage)





    }

    private fun increaseCoinValueInFirestore() {
        val userId = getUserId()
        val old_count = binding.buyCoinCoin.text.toString()
        val new_count : Int = old_count.toInt() + 50
        binding.buyCoinCoin.text = new_count.toString()

        userViewModel.updateCoinValue(userId , 100)
    }

    private fun getUserId(): String {
        // Firebase Authentication kullanılarak kullanıcının UID'sini al
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid ?: ""
    }

    private fun updateUI(userModel: UserModel) {
        binding.buyCoinCoin.text = userModel.coin.toString()
        Log.d("salam",userModel.fullName.toString())
        binding.buyCoinFullName.text = userModel.fullName.toString()
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
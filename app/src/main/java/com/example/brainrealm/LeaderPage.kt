package com.example.brainrealm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.brainrealm.Adapters.LeaderListAdapter
import com.example.brainrealm.Models.UserModel
import com.example.brainrealm.Models.UserViewModel
import com.example.brainrealm.databinding.ActivityLeaderPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class LeaderPage : AppCompatActivity() {
    lateinit var binding : ActivityLeaderPageBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: LeaderListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaderPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid.orEmpty()

        userViewModel.getUserData().observe(this, { userData ->
            userData?.let {
                updateUI(it)
            }
        })

        loadImageFromFirebaseStorage(userId.toString() , binding.leaderPageUserImage)




        val layoutManager = LinearLayoutManager(this)
        binding.leaderList.layoutManager = layoutManager

        userAdapter = LeaderListAdapter(this, mutableListOf())
        binding.leaderList.adapter = userAdapter

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        userViewModel.allUsers.observe(this, Observer { userList ->
            if (userList != null) {
                userAdapter.updateUserList(userList)
            } else {
                TODO()
            }
        })

        userViewModel.loadAllUsers()



    }

    private fun updateUI(userModel: UserModel) {
        binding.leaderPageCoin.text = userModel.coin.toString()
        Log.d("salam",userModel.fullName.toString())
        binding.leaderPageFullName.text = userModel.fullName.toString()
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
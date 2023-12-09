package com.example.brainrealm.Database

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class UserRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storageReference = FirebaseStorage.getInstance().reference

    fun signIn(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    fun createUser(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    fun uploadImage(imageUri: Uri, userId: String): UploadTask {
        val userImageRef = storageReference.child("user_images/$userId.jpg")
        return userImageRef.putFile(imageUri)
    }
}
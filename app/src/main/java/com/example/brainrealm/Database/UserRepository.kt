package com.example.brainrealm.Database

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brainrealm.Models.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class UserRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storageReference = FirebaseStorage.getInstance().reference

    // Sign in user with email and password
    fun signIn(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    // Create a new user with email and password
    fun createUser(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    // Upload user image to Firebase Storage
    fun uploadImage(imageUri: Uri, userId: String): UploadTask {
        val userImageRef = storageReference.child("user_images/$userId.jpg")
        return userImageRef.putFile(imageUri)
    }

    // Get user data from Firestore
    fun getUserData(userId: String): LiveData<UserModel?> {
        val userData = MutableLiveData<UserModel?>()

        // Create a reference to the Firestore document
        val userDocRef = FirebaseFirestore.getInstance().collection("users").document(userId)

        // Retrieve data from userDocRef
        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Convert the document data to UserModel
                    val userModel = document.toObject(UserModel::class.java)

                    // If UserModel is not null, create a new UserModel with necessary updates
                    val updatedUserModel = userModel?.copy(fullName = document.getString("fullName"))

                    userData.value = updatedUserModel
                } else {
                    // If no document in Firestore or an error occurs, update LiveData with null value
                    userData.value = null
                }
            }
            .addOnFailureListener { exception ->
                // Update LiveData with null value in case of an error
                userData.value = null
            }

        return userData
    }

    // Get all users from Firestore
    fun getAllUsers(): LiveData<List<UserModel>> {
        val usersLiveData = MutableLiveData<List<UserModel>>()

        // Retrieve all documents from the Firestore collection
        FirebaseFirestore.getInstance().collection("users")
            .get()
            .addOnSuccessListener { documents ->
                val userList = mutableListOf<UserModel>()

                for (document in documents) {
                    // Convert each document to UserModel and add to the list
                    val userModel = document.toObject(UserModel::class.java)
                    userList.add(userModel)
                }

                Log.d("repoTest", userList.size.toString())
                // Update LiveData
                usersLiveData.value = userList
            }
            .addOnFailureListener { exception ->
                // Handle operations in case of an error here
                Log.e("Firestore", "Error getting all users: $exception")
            }

        return usersLiveData
    }

    // Get all images for a specific user from Firebase Storage
    fun getAllImagesForUser(userId: String): LiveData<List<String>> {
        val imagesLiveData = MutableLiveData<List<String>>()

        // Get the Firebase Storage reference
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        // Get the UID of the current user
        val currentUserId = userId

        // Path where the user's profile image is stored
        val imagesPath = "user_images/$currentUserId.jpg"

        // Retrieve all images from a specific directory in Storage
        storageRef.child(imagesPath)
            .listAll()
            .addOnSuccessListener { result ->
                val imageList = result.items.map { it.toString() }
                Log.d("imageTestRepo", imageList.size.toString())
                imagesLiveData.value = imageList
            }
            .addOnFailureListener { exception ->
                // Handle operations in case of an error here
                Log.e("FirebaseStorage", "Error getting user images: $exception")
            }

        return imagesLiveData
    }
}

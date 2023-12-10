package com.example.brainrealm.Models

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.brainrealm.Database.UserRepository
import com.example.brainrealm.R
import com.example.brainrealm.Themes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel(application: Application, private val userRepository: UserRepository) : AndroidViewModel(application) {

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> get() = _user

    private val _imageUrl = MutableLiveData<String?>()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _allUsers = MutableLiveData<List<UserModel>>()
    val allUsers: LiveData<List<UserModel>> get() = _allUsers

    private val _userImages = MutableLiveData<List<String>>()
    val userImages: LiveData<List<String>> get() = _userImages

    private val _updateCoinResult = MutableLiveData<Boolean>()

    constructor(application: Application) : this(application, UserRepository()) {

    }

    init {

    }

    // Get all images for a specific user
    fun getAllImagesForUser(userId: String) {
        _userImages.value = userRepository.getAllImagesForUser(userId).value
    }

    // Load all users from the repository
    fun loadAllUsers() {
        userRepository.getAllUsers().observeForever { userList ->
            _allUsers.value = userList
        }
    }

    // Sign in user with email and password
    fun signIn(email: String, password: String) {
        userRepository.signIn(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userEmail = task.result?.user?.email ?: ""
                    val userPassword = "" // Password is not stored here for security reasons
                    val user = UserModel(userEmail, userPassword)
                    _user.value = user
                } else {
                    Toast.makeText(getApplication(), "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Create a new user with email and password
    fun createUser(email: String, password: String, passwordEditText: EditText) {
        userRepository.createUser(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User creation successful
                    val user = task.result?.user
                    user?.let {
                        // Get the document reference for the user
                        val userDocRef = FirebaseFirestore.getInstance().collection("users").document(it.uid)

                        // Create the document and add default values
                        val userDefaults = hashMapOf(
                            "coin" to 0,
                            "xal" to 0,
                            "fullName" to ""
                        )

                        userDocRef.set(userDefaults)
                            .addOnSuccessListener {
                                // Successfully added to Firestore
                                val intent = Intent(getApplication(), Themes::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                getApplication<Application>().startActivity(intent)

                                // Finish the current activity when navigating to the next one
                                (passwordEditText.context as? AppCompatActivity)?.finish()
                            }
                            .addOnFailureListener { e ->
                                // Error occurred while adding to Firestore
                                Toast.makeText(getApplication(), "Error adding user data to Firestore: $e", Toast.LENGTH_SHORT).show()

                                // Restore the visibility of the button and hide the progress bar
                                (passwordEditText.context as? AppCompatActivity)?.run {
                                    findViewById<View>(R.id.register_button)?.isVisible = true
                                    findViewById<View>(R.id.register_progress_bar)?.isVisible = false
                                }
                            }
                    }
                } else {
                    // User creation failed, show error message
                    Toast.makeText(getApplication(), "User creation failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()

                    // Restore the visibility of the button and hide the progress bar
                    (passwordEditText.context as? AppCompatActivity)?.run {
                        findViewById<View>(R.id.register_button)?.isVisible = true
                        findViewById<View>(R.id.register_progress_bar)?.isVisible = false
                    }
                }
            }
    }

    // Update the coin value for a user in Firestore
    fun updateCoinValue(userId: String, amount: Int) {
        // Create a reference to the Firestore document
        val userDocRef = FirebaseFirestore.getInstance().collection("users").document(userId)

        // Retrieve data from userDocRef
        userDocRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Convert the document data to UserModel
                    val userModel = document.toObject(UserModel::class.java)

                    // Get the current coin value
                    val currentCoin = userModel?.coin ?: 0

                    // Increase the coin value by the specified amount
                    val newCoinValue = currentCoin + amount

                    // Update the coin value in the UserModel
                    userModel?.coin = newCoinValue

                    // Write the updated UserModel to Firestore
                    userDocRef.set(userModel!!)
                        .addOnSuccessListener {
                            // Successfully written
                            _updateCoinResult.value = true
                        }
                        .addOnFailureListener { exception ->
                            // Handle operations in case of an error
                            Log.e("Firestore", "Error updating coin value: $exception")
                            _updateCoinResult.value = false
                        }
                } else {
                    // If no document in Firestore or an error occurs, update LiveData with false value
                    Log.e("Firestore", "User document does not exist")
                    _updateCoinResult.value = false
                }
            }
            .addOnFailureListener { exception ->
                // Update LiveData with false value in case of an error
                Log.e("Firestore", "Error getting user document: $exception")
                _updateCoinResult.value = false
            }
    }

    // Upload user image to Firebase Storage
    fun uploadImage(imageUri: Uri, userId: String) {
        userRepository.uploadImage(imageUri, userId)
            .addOnSuccessListener {
                // Actions to be taken on successful upload
                // For example, showing a message to the user
                Toast.makeText(getApplication(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                // Actions to be taken in case of an error
                Toast.makeText(getApplication(), "Image upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Get user data from Firestore
    fun getUserData(): LiveData<UserModel?> {
        return userRepository.getUserData(userId.toString())
    }
}

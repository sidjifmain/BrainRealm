package com.example.brainrealm.Models

import android.app.Application
import android.content.Intent
import android.net.Uri
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

class UserViewModel(application: Application, private val userRepository: UserRepository) : AndroidViewModel(application) {

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> get() = _user

    init {
        // Bu blok, sınıf oluşturulduğunda çalışacaktır.
        // Burada başka başlatma işlemleri de ekleyebilirsiniz.
    }

    // Başlatıcı ekleyin
    constructor(application: Application) : this(application, UserRepository()) {
        // Eğer UserRepository'yi özel bir şekilde başlatmak istiyorsanız burada başlatma işlemini yapabilirsiniz.
    }

    fun signIn(email: String, password: String) {
        userRepository.signIn(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userEmail = task.result?.user?.email ?: ""
                    val userPassword = "" // Parola burada bilinmez, güvenlik amacıyla saklanmaz
                    val user = UserModel(userEmail, userPassword)
                    _user.value = user
                } else {
                    Toast.makeText(getApplication(), "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun createUser(email: String, password: String, passwordEditText: EditText) {
        userRepository.createUser(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(getApplication(), Themes::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    getApplication<Application>().startActivity(intent)

                    // Finish the current activity when navigating to the next one
                    (passwordEditText.context as? AppCompatActivity)?.finish()
                } else {
                    Toast.makeText(getApplication(), "User creation failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    // Restore the visibility of the button and hide the progress bar
                    // Bu satırları güncelledim
                    (passwordEditText.context as? AppCompatActivity)?.run {
                        findViewById<View>(R.id.register_button)?.isVisible = true
                        findViewById<View>(R.id.register_progress_bar)?.isVisible = false
                    }
                }
            }


    }

    fun uploadImage(imageUri: Uri, userId: String) {
        userRepository.uploadImage(imageUri, userId)
            .addOnSuccessListener {
                // Başarılı yükleme durumunda yapılacak işlemler
                // Örneğin, kullanıcıya bir mesaj göstermek gibi
                Toast.makeText(getApplication(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                // Hata durumunda yapılacak işlemler
                Toast.makeText(getApplication(), "Image upload failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

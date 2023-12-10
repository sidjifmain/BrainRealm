package com.example.brainrealm.Models

data class UserModel(
    val userEmail: String,
    val userPassword: String,
    val fullName : String? = "Mehdi Israfilov",
    var coin: Int = 0,
    var xal: Int = 0,
){
    constructor() : this("" , "" , "Mehdi Israfilov" ,0 ,0)
}
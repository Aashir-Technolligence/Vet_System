package com.aash.vetsystem.models

data class UserModel(
    val userId: String,
    val fullName: String,
    val mobile: String,
    val whatsApp: String,
    var profileImage: String,
    val password: String,
    val email: String,
    val fingerConfigured: Boolean
)

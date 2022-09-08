package com.aash.vetsystem.models

data class UserModel(
    var userId: String,
    var fullName: String,
    var mobile: String,
    var whatsApp: String,
    var profileImage: String,
    var password: String,
    var email: String,
    var fingerConfigured: Boolean
)

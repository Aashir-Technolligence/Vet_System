package com.aash.vetsystem.models

data class MyOrderModel(
    var item: String,
    var quantity: String,
    var price: String,
    var payStatus: String,
    var comment: String,
    var payVia: String,
    var payScreenshot: String
)
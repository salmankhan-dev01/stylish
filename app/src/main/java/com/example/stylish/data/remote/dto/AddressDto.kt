package com.example.stylish.data.remote.dto

data class AddressDto(
    var pinCode: String = "",
    var address: String = "",
    var city: String = "",
    var state: String = "",
    var country: String = ""
)
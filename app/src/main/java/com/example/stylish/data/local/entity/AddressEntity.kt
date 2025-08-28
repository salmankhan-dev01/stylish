package com.example.stylish.data.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity(tableName = "address_table")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)

    val id: Int = 0,  // Room ke liye

    val pinCode: String,
    val address: String,
    val city: String,
    val state: String,
    val country: String
)
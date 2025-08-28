package com.example.stylish.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_account_table")
data class BankAccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,  // Room ke liye
    val accountHolder: String,
    val accountNumber: String,
    val ifscCode: String
)
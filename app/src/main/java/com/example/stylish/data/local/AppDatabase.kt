package com.example.stylish.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stylish.data.local.dto.AddressDao
import com.example.stylish.data.local.dto.BankAccountDao
import com.example.stylish.data.local.entity.AddressEntity
import com.example.stylish.data.local.entity.BankAccountEntity

@Database(
    entities = [AddressEntity::class, BankAccountEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
    abstract fun bankAccountDao(): BankAccountDao
}
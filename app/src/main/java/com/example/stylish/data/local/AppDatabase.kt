package com.example.stylish.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stylish.data.local.dao.UserDao
import com.example.stylish.data.local.dto.AddressDao
import com.example.stylish.data.local.dto.BankAccountDao
import com.example.stylish.data.local.entity.AddressEntity
import com.example.stylish.data.local.entity.BankAccountEntity
import com.example.stylish.data.local.entity.UserEntity

@Database(
    entities = [AddressEntity::class, BankAccountEntity::class, UserEntity::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao
    abstract fun bankAccountDao(): BankAccountDao

    abstract fun userDao(): UserDao
}
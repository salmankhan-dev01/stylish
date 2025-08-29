package com.example.stylish.data.local.dto

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stylish.data.local.entity.AddressEntity

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(address: AddressEntity)

    @Query("SELECT * FROM address_table ORDER BY id DESC LIMIT 1")
    suspend fun getAddress(): AddressEntity?

//
//    @Query("SELECT * FROM address_table WHERE syncPending = 1")
//    suspend fun getPendingAddresses(): List<AddressEntity> // sync ke liye

    @Query("DELETE FROM address_table")
    suspend fun clear()
}
package com.example.stylish.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stylish.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM user_table ORDER BY id DESC LIMIT 1")
    suspend fun getLatestUser(): UserEntity?

    @Query("DELETE FROM user_table")
    suspend fun clear()
}
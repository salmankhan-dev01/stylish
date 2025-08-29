package com.example.stylish.data.local.dto
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stylish.data.local.entity.BankAccountEntity

@Dao
interface BankAccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: BankAccountEntity)

    @Query("SELECT * FROM bank_account_table ORDER BY id DESC LIMIT 1")
    suspend fun getAccount(): BankAccountEntity?

    @Query("DELETE FROM bank_account_table")
    suspend fun clear()
}
package com.example.stylish.data.local

import android.content.Context
import androidx.room.Room

object AppDatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "stylish_database"
            )
                .fallbackToDestructiveMigration() // 👈 old data delete, new schema create
                .build()
            INSTANCE = instance
            instance
        }
    }
}

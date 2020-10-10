package com.vs.trendz.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vs.trendz.model.Converters
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.util.DATABASE_NAME

@Database(entities = [TrendingRepositoryResponseData::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return (instance ?: synchronized(this) {
                instance ?: database(context).also { instance = it }
            })
        }

        private fun database(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }


}
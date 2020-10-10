package com.vs.trendz.repository

import android.content.Context
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.roomdb.AppDatabase

class SearchHistoryRepository private constructor(
    appDatabase: AppDatabase,
    commonRepository: CommonRepository,
    applicationContext: Context
) {


    companion object {

        @Volatile
        var instance: SearchHistoryRepository? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: SearchHistoryRepository(
                AppDatabase.getInstance(context),
                CommonRepository.getInstance(context, AppDatabase.getInstance(context)),
                context.applicationContext
            ).also { instance = it }
        }
    }


    fun insertIntoLocalDb(arrayList: ArrayList<TrendingRepositoryResponseData>) {

    }


}
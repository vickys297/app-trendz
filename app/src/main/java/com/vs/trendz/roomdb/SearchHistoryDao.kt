package com.vs.trendz.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vs.trendz.model.TrendingRepositoryResponseData


@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoLocalHistory(responseData: ArrayList<TrendingRepositoryResponseData>)

    @Query("SELECT * FROM repo_local LIMIT 10")
    fun getAllLocalRepo(): List<TrendingRepositoryResponseData>

}
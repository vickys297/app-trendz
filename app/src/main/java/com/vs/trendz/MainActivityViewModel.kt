package com.vs.trendz

import androidx.lifecycle.ViewModel
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.repository.CommonRepository

class MainActivityViewModel internal constructor
    (
    private val repository: CommonRepository
) :
    ViewModel() {


    fun getAllDetailsFromLocal(): List<TrendingRepositoryResponseData> {
        return repository.GetAllData().execute().get()
    }

    fun checkNetworkConnection(): Boolean {
        return repository.networkConnection.connectionAvailable()
    }

}
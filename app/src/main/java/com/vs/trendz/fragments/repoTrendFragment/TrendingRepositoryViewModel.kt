package com.vs.trendz.fragments.repoTrendFragment

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.repository.CommonRepository

class TrendingRepositoryViewModel internal constructor(
    private val repository: CommonRepository,
    private val applicationContext: Context
) : AndroidViewModel(applicationContext as Application) {


    val responseRepositoryData = MutableLiveData<ArrayList<TrendingRepositoryResponseData>>()


    // request for new data from remote server
    fun requestTrendingRepoList(): MutableLiveData<ArrayList<TrendingRepositoryResponseData>> {
        println("getTrendingRepoList")
        repository.requestTrendingRepoList(responseRepositoryData)
        return responseRepositoryData
    }

    // insert new data from local database
    fun insertTrendingRepositoryInLocalDatabase(arrayList: ArrayList<TrendingRepositoryResponseData>) {
        repository.InsertIntoLocalDatabase(arrayList).execute()
    }


    // get all data from local server
    fun getAllDetailsFromLocal(): List<TrendingRepositoryResponseData> {
        return repository.GetAllData().execute().get()
    }

    fun checkNetworkConnection(): Boolean {
        return repository.networkConnection.connectionAvailable()
    }

}
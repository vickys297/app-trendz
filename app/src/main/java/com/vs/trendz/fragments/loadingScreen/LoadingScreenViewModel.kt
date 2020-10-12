package com.vs.trendz.fragments.loadingScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.repository.CommonRepository

class LoadingScreenViewModel internal constructor
    (
    private val repository: CommonRepository
) :
    ViewModel() {

    private val responseRepositoryData =
        MutableLiveData<ArrayList<TrendingRepositoryResponseData>>()


    fun insertTrendingRepositoryInLocalDatabase(arrayList: ArrayList<TrendingRepositoryResponseData>) {
        repository.InsertIntoLocalDatabase(arrayList).execute()
    }

    fun requestTrendingRepoList(): MutableLiveData<ArrayList<TrendingRepositoryResponseData>> {
        repository.requestTrendingRepoList(responseRepositoryData)
        return responseRepositoryData
    }

}
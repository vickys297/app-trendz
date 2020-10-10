package com.vs.trendz.fragments.repoTrendFragment

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.repository.CommonRepository

class TrendingRepositoryViewModel internal constructor(
    private val repository: CommonRepository,
    private val applicationContext: Context
) : AndroidViewModel(applicationContext as Application) {


    val responseRepositoryData = MutableLiveData<ArrayList<TrendingRepositoryResponseData>>()


    fun getTrendingRepoList(): MutableLiveData<ArrayList<TrendingRepositoryResponseData>> {
        println("getTrendingRepoList")
        repository.getTrendingRepository(responseRepositoryData)
        return responseRepositoryData
    }

    fun insertIntoLocalSearch(arrayList: ArrayList<TrendingRepositoryResponseData>) {
        repository.insertIntoLocalDatabase(arrayList).execute()
    }


    fun getAllDetails(): List<TrendingRepositoryResponseData> {
        return repository.getAllData().execute().get()
    }

}
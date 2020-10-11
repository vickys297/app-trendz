package com.vs.trendz

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.repository.CommonRepository

class MainActivityViewModel internal constructor
    (
    private val repository: CommonRepository,
    val applicationContext: Context
) :
    ViewModel() {

    val responseRepositoryData = MutableLiveData<ArrayList<TrendingRepositoryResponseData>>()

    fun checkDataAvailable(): Boolean {

        //check if data available in database
        val localData = getAllDetailsFromLocal() as ArrayList<TrendingRepositoryResponseData>
        return !localData.isNullOrEmpty()
    }

    fun getAllDetailsFromLocal(): List<TrendingRepositoryResponseData> {
        return repository.GetAllData().execute().get()
    }

    fun insertTrendingRepositoryInLocalDatabase(arrayList: ArrayList<TrendingRepositoryResponseData>) {
        repository.InsertIntoLocalDatabase(arrayList).execute()
    }

    fun requestTrendingRepoList(): MutableLiveData<ArrayList<TrendingRepositoryResponseData>> {
        repository.requestTrendingRepoList(responseRepositoryData)
        return responseRepositoryData
    }


}
package com.vs.trendz.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.vs.trendz.api.APIServices
import com.vs.trendz.api.RetrofitService
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.roomdb.AppDatabase
import com.vs.trendz.util.NetworkStatus
import retrofit2.Call
import retrofit2.Response


/*
* Common Repository
* Dao and remote api call
*
* */


class CommonRepository private constructor(
    val context: Context,
    appDatabase: AppDatabase,
    private val networkStatus: NetworkStatus
) {

    val searchHistoryDao = appDatabase.searchHistoryDao()


    companion object {

        @Volatile
        private var instance: CommonRepository? = null

        fun getInstance(context: Context, appDatabase: AppDatabase, networkStatus: NetworkStatus) =
            this.instance ?: synchronized(this) {
                this.instance ?: CommonRepository(
                    context,
                    appDatabase,
                    networkStatus
                ).also { this.instance = it }
            }
    }

    fun requestTrendingRepoList(trendingRepositoryResponseData: MutableLiveData<ArrayList<TrendingRepositoryResponseData>>) {

        // check if network connection is available

        if (networkStatus.connectionAvailable()) {
            val client = RetrofitService.getService().create(APIServices::class.java)
            val request = client.getTrendingList()

            println("Http Start")

            request.enqueue(object :
                retrofit2.Callback<ArrayList<TrendingRepositoryResponseData>> {
                override fun onResponse(
                    call: Call<ArrayList<TrendingRepositoryResponseData>>,
                    trendingResponse: Response<ArrayList<TrendingRepositoryResponseData>>
                ) {
                    val body = trendingResponse.body()
                    println("response ----\n${Gson().toJson(body)}\n")
                    try {
                        if (trendingResponse.isSuccessful && trendingResponse.body() != null) {
                            trendingRepositoryResponseData.value = body
                        } else {
                            trendingRepositoryResponseData.value = null
                        }
                    } catch (t: Exception) {
                        trendingRepositoryResponseData.value = null
                        println("Error-----\n$t\n----")
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<TrendingRepositoryResponseData>>,
                    t: Throwable
                ) {
                    trendingRepositoryResponseData.value = null
                    println("Error-----\n$t\n----")
                }

            })

            println("Http End")
        }
    }


    // get all data from local database
    inner class GetAllData : AsyncTask<Void, Void, List<TrendingRepositoryResponseData>>() {
        override fun doInBackground(vararg params: Void?): List<TrendingRepositoryResponseData> {
            return searchHistoryDao.getAllLocalRepo()
        }
    }

    // get new data from local database
    inner class InsertIntoLocalDatabase(private val arrayList: ArrayList<TrendingRepositoryResponseData>) :
        AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            searchHistoryDao.insertIntoLocalHistory(arrayList)
        }
    }


}
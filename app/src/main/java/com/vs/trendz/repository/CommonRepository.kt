package com.vs.trendz.repository

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import com.vs.trendz.api.APIServices
import com.vs.trendz.api.RetrofitService
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.roomdb.AppDatabase
import retrofit2.Call
import retrofit2.Response

class CommonRepository private constructor(
    val context: Context,
    private val appDatabase: AppDatabase
) {

    val searchHistoryDao = appDatabase.searchHistoryDao()


    companion object {

        @Volatile
        private var instance: CommonRepository? = null

        fun getInstance(context: Context, appDatabase: AppDatabase) =
            instance ?: synchronized(this) {
                instance ?: CommonRepository(context, appDatabase).also { instance = it }
            }
    }

    fun getTrendingRepository(trendingRepositoryResponseData: MutableLiveData<ArrayList<TrendingRepositoryResponseData>>) {
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

                println("response ----\n$body\n")
                try {
                    val body = trendingResponse.body()
                    if (trendingResponse.isSuccessful && trendingResponse.body() != null) {
                        trendingRepositoryResponseData.value = body
                    } else {
                        trendingRepositoryResponseData.value = null
                    }
                } catch (t: Exception) {
                    trendingRepositoryResponseData.value = null
                    println("response ----\nError-----\n$t\n----")
                }
            }

            override fun onFailure(
                call: Call<ArrayList<TrendingRepositoryResponseData>>,
                t: Throwable
            ) {
                trendingRepositoryResponseData.value = null
            }

        })

    }

    inner class getAllData() : AsyncTask<Void, Void, List<TrendingRepositoryResponseData>>() {
        override fun doInBackground(vararg params: Void?): List<TrendingRepositoryResponseData> {
            return searchHistoryDao.getAllLocalRepo()
        }
    }


    inner class insertIntoLocalDatabase(private val arrayList: ArrayList<TrendingRepositoryResponseData>) :
        AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            searchHistoryDao.insertIntoLocalHistory(arrayList)
        }
    }


}
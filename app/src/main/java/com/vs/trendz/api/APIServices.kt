package com.vs.trendz.api

import com.vs.trendz.model.TrendingRepositoryResponseData
import retrofit2.Call
import retrofit2.http.GET

interface APIServices {

    @GET("repositories")
    fun getTrendingList(): Call<ArrayList<TrendingRepositoryResponseData>>

}
package com.vs.trendz.fragments.noNetworkFragment

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.repository.CommonRepository

class NoNetworkConnectionViewModel
internal constructor(
    private val repository: CommonRepository,
    private val applicationContext: Context
) : AndroidViewModel(applicationContext as Application) {




}
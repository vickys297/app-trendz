package com.vs.trendz.fragments

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vs.trendz.fragments.repoTrendFragment.TrendingRepositoryViewModel
import com.vs.trendz.fragments.searchRepoFragment.SearchRepoViewModel
import com.vs.trendz.repository.CommonRepository
import com.vs.trendz.roomdb.AppDatabase

class CommonViewModelFactory(private val context: Context?) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass.simpleName) {
            "TrendingRepositoryViewModel" -> {
                TrendingRepositoryViewModel(
                    CommonRepository.getInstance(context!!, AppDatabase.getInstance(context)),
                    context.applicationContext
                ) as T
            }
            else -> {
                SearchRepoViewModel(
                    CommonRepository.getInstance(context!!, AppDatabase.getInstance(context)),
                    context.applicationContext
                ) as T
            }
        }
    }


}
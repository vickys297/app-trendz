package com.vs.trendz.fragments

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vs.trendz.MainActivityViewModel
import com.vs.trendz.fragments.loadingScreen.LoadingScreenViewModel
import com.vs.trendz.fragments.noNetworkFragment.NoNetworkConnectionViewModel
import com.vs.trendz.fragments.repoTrendFragment.TrendingRepositoryViewModel
import com.vs.trendz.repository.CommonRepository
import com.vs.trendz.roomdb.AppDatabase
import com.vs.trendz.util.NetworkStatus

class CommonViewModelFactory(private val context: Context?) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass.simpleName) {
            TrendingRepositoryViewModel::class.java.simpleName -> {
                TrendingRepositoryViewModel(
                    CommonRepository.getInstance(
                        context!!,
                        AppDatabase.getInstance(context),
                        NetworkStatus.getInstance(context)
                    ),
                    context.applicationContext
                ) as T
            }
            MainActivityViewModel::class.java.simpleName -> {
                MainActivityViewModel(
                    CommonRepository.getInstance(
                        context!!,
                        AppDatabase.getInstance(context),
                        NetworkStatus.getInstance(context)
                    )
                ) as T
            }
            LoadingScreenViewModel::class.java.simpleName -> {
                LoadingScreenViewModel(
                    CommonRepository.getInstance(
                        context!!,
                        AppDatabase.getInstance(context),
                        NetworkStatus.getInstance(context)
                    )
                ) as T
            }
            else -> {
                NoNetworkConnectionViewModel(
                    context!!.applicationContext
                ) as T
            }
        }
    }
}
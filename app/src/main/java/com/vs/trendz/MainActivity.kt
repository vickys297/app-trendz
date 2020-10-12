package com.vs.trendz

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vs.trendz.databinding.ActivityMainBinding
import com.vs.trendz.fragments.CommonViewModelFactory
import com.vs.trendz.model.TrendingRepositoryResponseData

class MainActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var binding: ActivityMainBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set data binding
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.progressBar.visibility = View.GONE

        setupViewModel()
        setupController()
    }


    private fun setupController() {
        binding.networkIssueText.visibility = View.GONE
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onResume() {
        super.onResume()

        //check if data available in database
        val localData =
            mainActivityViewModel.getAllDetailsFromLocal() as ArrayList<TrendingRepositoryResponseData>

        /*
        * if Network and local data available
        * then:
        * show loading and sync data with remote serve
        *
        * else if check no network and local storage data available
        * then:
        * show offline data
        *
        * else if check if network available and data not available
        * then:
        * show loading screen and sync with remote serve
        *
        * else
        * show no network connection from loading fragment
        *
        * */


        val networkStatus = mainActivityViewModel.checkNetworkConnection()

        if (networkStatus && !localData.isNullOrEmpty()) {

            //show trending list
            showLoadingScreenFragment()
        } else if (!networkStatus && !localData.isNullOrEmpty()) {

            // show trending list
            showTrendingRepoList()
        } else if (networkStatus && localData.isNullOrEmpty()) {

            // show loading screen
            showLoadingScreenFragment()
        } else {

            // show no network
            showNoDataAndNetworkConnection()
        }


    }


    private fun setupViewModel() {

        mainActivityViewModel = ViewModelProvider(
            this,
            CommonViewModelFactory(this@MainActivity)
        ).get(MainActivityViewModel::class.java)

        binding.apply {
            mainViewModel = this@MainActivity.mainActivityViewModel
            lifecycleOwner = this@MainActivity
            executePendingBindings()
        }
    }

    private fun showLoadingScreenFragment() {
        // redirect to loading fragment
        navController.popBackStack()
        navController.navigate(R.id.loadingScreen)
    }


    private fun showTrendingRepoList() {
        // redirect to trending fragment
        navController.popBackStack()
        navController.navigate(R.id.trendingRepositoryFragment)

    }


    private fun showNoDataAndNetworkConnection() {
        // redirect to no network fragment from loading page
        navController.navigate(R.id.action_loadingScreen_to_noNetworkConnectionFragment)
    }
}
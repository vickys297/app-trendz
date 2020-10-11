package com.vs.trendz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vs.trendz.databinding.ActivityMainBinding
import com.vs.trendz.fragments.CommonViewModelFactory
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.util.NetworkStatus

class MainActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var binding: ActivityMainBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set data binding
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.progressBar.visibility = View.VISIBLE

        setupViewModel()
        setupObserver()
    }



    override fun onResume() {
        super.onResume()

        //check if data available in database
        val localData =
            mainActivityViewModel.getAllDetailsFromLocal() as ArrayList<TrendingRepositoryResponseData>


        /*
        * Check if Network and local data available
        * No:
        * setup nav controller and got to no network fragment and download content
        *
        * else check local storage data is empty
        * Yes:
        * request for new data
        *
        * else
        * stop progress bar and setup controller and default destination to trending repository
        *
        * */


        if (!NetworkStatus.getInstance(this@MainActivity)
                .connectionAvailable() && localData.isNullOrEmpty()
        ) {

            // stop progress bar
            binding.progressBar.visibility = View.GONE

            // set controller and redirect to no network fragment
            setupController()
            // redirect
            showNoNetworkConnection()

        } else if (localData.isNullOrEmpty()) {


            // get data from remote server
            mainActivityViewModel.requestTrendingRepoList()


        } else {

            // stop progress bar
            binding.progressBar.visibility = View.GONE

            // show content
            setupController()

        }

    }


    private fun setupObserver() {
        mainActivityViewModel.responseRepositoryData.observe(this@MainActivity, Observer {

            // stop progress bar
            binding.progressBar.visibility = View.GONE

            if (!it.isNullOrEmpty()) {
                mainActivityViewModel.insertTrendingRepositoryInLocalDatabase(it)
                setupController()
            } else {
                showNoDataMessage()
            }
        })
    }

    private fun showNoDataMessage() {
        Toast.makeText(
            this@MainActivity,
            getString(R.string.data_update_failed),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
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


    private fun showNoNetworkConnection() {

        // redirect to no network fragment
        navController.navigate(R.id.action_trendingRepositoryFragment_to_noNetworkConnectionFragment)
    }

}
package com.vs.trendz.fragments.loadingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vs.trendz.R
import com.vs.trendz.databinding.LoadingScreenFragmentBinding
import com.vs.trendz.fragments.CommonViewModelFactory
import com.vs.trendz.model.TrendingRepositoryResponseData
import com.vs.trendz.util.NetworkStatus
import kotlinx.android.synthetic.main.loading_screen_fragment.*

class LoadingScreen : Fragment() {

    companion object {
        fun newInstance() = LoadingScreen()
    }

    private lateinit var viewModel: LoadingScreenViewModel
    private lateinit var observer: Observer<ArrayList<TrendingRepositoryResponseData>>

    private lateinit var binding: LoadingScreenFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.loading_screen_fragment, container, false)
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity(), CommonViewModelFactory(context)).get(
            LoadingScreenViewModel::class.java
        )
        binding.apply {
            lifecycleOwner = this@LoadingScreen
            executePendingBindings()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupObserver()

        (activity as AppCompatActivity).setSupportActionBar(loading_toolbar)


        binding.btnRetry.setOnClickListener {


            binding.errorState.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE

            // on button click try again
            requestTrendingRepoData()
        }
    }

    private fun requestTrendingRepoData() {
        if (NetworkStatus.getInstance(requireContext()).connectionAvailable()) {

            // try requesting data
            viewModel.requestTrendingRepoList().observe(viewLifecycleOwner, observer)
        } else {
            findNavController().navigate(R.id.action_loadingScreen_to_noNetworkConnectionFragment)
        }
    }

    private fun setupObserver() {
        observer = Observer {

            it?.let {
                if (!it.isNullOrEmpty()) {
                    viewModel.insertTrendingRepositoryInLocalDatabase(it)

                    // if data available clear back stack and got to trending fragment
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.trendingRepositoryFragment)
                }
            }

            // if unable to get the data show try again

            it ?: run {
                binding.errorState.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()

        requestTrendingRepoData()
    }

}
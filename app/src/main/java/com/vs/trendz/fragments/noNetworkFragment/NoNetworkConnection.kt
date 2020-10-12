package com.vs.trendz.fragments.noNetworkFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vs.trendz.R
import com.vs.trendz.databinding.SearchRepoFragmentBinding
import com.vs.trendz.fragments.CommonViewModelFactory
import com.vs.trendz.util.NetworkStatus
import kotlinx.android.synthetic.main.search_repo_fragment.*

/*
* No Connection state fragment
* */
class NoNetworkConnection : Fragment() {

    companion object {
        fun newInstance() = NoNetworkConnection()
    }

    private lateinit var viewModel: NoNetworkConnectionViewModel
    private lateinit var binding: SearchRepoFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_repo_fragment, container, false)
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity(), CommonViewModelFactory(context)).get(
            NoNetworkConnectionViewModel::class.java
        )
        binding.apply {
            noNetworkViewModel = this@NoNetworkConnection.viewModel
            lifecycleOwner = this@NoNetworkConnection
            executePendingBindings()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity?)!!.setSupportActionBar(search_toolbar)
    }


    override fun onResume() {
        super.onResume()

        binding.btnTryagain.setOnClickListener {
            if (NetworkStatus.getInstance(requireContext()).connectionAvailable()) {

                // just navigate back to the start fragment
                findNavController().navigateUp()
            } else {

                // show alert to check connection
                showAlertMessage()

            }
        }

    }

    private fun showAlertMessage() {
        Toast.makeText(requireContext(), getString(R.string.alert_no_network), Toast.LENGTH_SHORT)
            .show()
    }


}
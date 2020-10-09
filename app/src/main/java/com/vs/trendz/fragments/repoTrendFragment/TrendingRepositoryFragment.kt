package com.vs.trendz.fragments.repoTrendFragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vs.trendz.R

class TrendingRepositoryFragment : Fragment() {

    companion object {
        fun newInstance() = TrendingRepositoryFragment()
    }

    private lateinit var viewModel: TrendingRepositoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trending_repository_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TrendingRepositoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
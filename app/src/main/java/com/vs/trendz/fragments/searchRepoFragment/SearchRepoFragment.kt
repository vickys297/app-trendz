package com.vs.trendz.fragments.searchRepoFragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vs.trendz.R

class SearchRepoFragment : Fragment() {

    companion object {
        fun newInstance() = SearchRepoFragment()
    }

    private lateinit var viewModel: SearchRepoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_repo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchRepoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
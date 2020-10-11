package com.vs.trendz.fragments.searchRepoFragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.vs.trendz.R
import com.vs.trendz.databinding.SearchRepoFragmentBinding
import com.vs.trendz.fragments.CommonViewModelFactory
import kotlinx.android.synthetic.main.search_repo_fragment.*


class SearchRepoFragment : Fragment() {

    companion object {
        fun newInstance() = SearchRepoFragment()
    }

    private lateinit var viewModel: SearchRepoViewModel
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
        viewModel = ViewModelProviders.of(requireActivity(), CommonViewModelFactory(context)).get(
            SearchRepoViewModel::class.java
        )
        binding.apply {
            searchViewModel = this@SearchRepoFragment.viewModel
            lifecycleOwner = this@SearchRepoFragment
            executePendingBindings()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(search_toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_close, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    private fun clearSearch() {
        TODO("Not yet implemented")
    }


}
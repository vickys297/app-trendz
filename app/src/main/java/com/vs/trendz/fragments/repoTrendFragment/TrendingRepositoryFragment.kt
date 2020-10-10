package com.vs.trendz.fragments.repoTrendFragment

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vs.trendz.R
import com.vs.trendz.databinding.TrendingRepositoryFragmentBinding
import com.vs.trendz.fragments.CommonViewModelFactory
import com.vs.trendz.fragments.repoTrendFragment.adapter.TrendingListAdapter
import com.vs.trendz.model.TrendingRepositoryResponseData
import kotlinx.android.synthetic.main.trending_repository_fragment.*

class TrendingRepositoryFragment : Fragment() {

    companion object {
        fun newInstance() = TrendingRepositoryFragment()
    }

    private lateinit var responseDataObserver: Observer<ArrayList<TrendingRepositoryResponseData>>

    //    private lateinit var viewModel: TrendingRepositoryViewModel
    private lateinit var adapter: TrendingListAdapter
    private lateinit var viewModel: TrendingRepositoryViewModel

    private lateinit var binding: TrendingRepositoryFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.trending_repository_fragment,
            container,
            false
        )

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProviders.of(context as FragmentActivity, CommonViewModelFactory(context))
                .get(TrendingRepositoryViewModel::class.java)
        binding.apply {
            trendingViewModel = this@TrendingRepositoryFragment.viewModel
            lifecycleOwner = this@TrendingRepositoryFragment
            executePendingBindings()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TrendingListAdapter(requireContext())
        showLoadingState()
        setupObserver()
    }

    private fun showLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.swipeToRefresh.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        showLoadingState()
        viewModel.getTrendingRepoList()

        val layoutManager = LinearLayoutManager(requireActivity())

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        swipe_to_refresh.setOnRefreshListener {
            viewModel.getTrendingRepoList()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_search -> {
                findNavController().navigate(R.id.action_trendingRepositoryFragment_to_searchRepoFragment)
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupObserver() {
        responseDataObserver = Observer {
            if (!it.isNullOrEmpty()) {
                if (swipe_to_refresh.isRefreshing)
                    swipe_to_refresh.isRefreshing = false

                if (binding.swipeToRefresh.visibility == View.GONE) {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeToRefresh.visibility = View.VISIBLE
                }
                adapter.updateList(it)
                viewModel.insertIntoLocalSearch(it)
            }

        }
        viewModel.responseRepositoryData.observe(viewLifecycleOwner, responseDataObserver)
    }

}
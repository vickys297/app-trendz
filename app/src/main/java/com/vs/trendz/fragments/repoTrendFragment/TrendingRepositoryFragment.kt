package com.vs.trendz.fragments.repoTrendFragment

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vs.trendz.R
import com.vs.trendz.databinding.TrendingRepositoryFragmentBinding
import com.vs.trendz.fragments.CommonViewModelFactory
import com.vs.trendz.fragments.adapter.TrendingListAdapter
import com.vs.trendz.model.TrendingRepositoryResponseData
import kotlinx.android.synthetic.main.trending_repository_fragment.*
import java.util.*
import kotlin.collections.ArrayList

/*
* List Trending Repository Page
* */
class TrendingRepositoryFragment : Fragment(), Filterable {

    companion object {
        fun newInstance() = TrendingRepositoryFragment()
    }

    private lateinit var responseDataObserver: Observer<ArrayList<TrendingRepositoryResponseData>>
    private lateinit var adapter: TrendingListAdapter
    private lateinit var viewModel: TrendingRepositoryViewModel
    private lateinit var binding: TrendingRepositoryFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
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
            ViewModelProvider(context as FragmentActivity, CommonViewModelFactory(context))
                .get(TrendingRepositoryViewModel::class.java)
        binding.apply {
            trendingViewModel = this@TrendingRepositoryFragment.viewModel
            lifecycleOwner = this@TrendingRepositoryFragment
            executePendingBindings()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)

        // init adapter
        adapter = TrendingListAdapter(requireContext())

        val layoutManager = LinearLayoutManager(requireActivity())

        // init divider
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )

        // set divider, layout manager and adapter
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        setupObserver()
    }


    override fun onResume() {
        super.onResume()

        showLoadingState()

        //check if data available in database
        val localData =
            viewModel.getAllDetailsFromLocal() as ArrayList<TrendingRepositoryResponseData>

        if (!localData.isNullOrEmpty()) {

            // update the recycler view list
            updateRecyclerView(localData)

            // show content
            showContent()
        }

        //Swipe to refresh 
        swipe_to_refresh.setOnRefreshListener {
            checkNetworkConnection()
        }

    }

    private fun checkNetworkConnection() {
        if (viewModel.checkNetworkConnection()) {

            // get data from remote server
            viewModel.requestTrendingRepoList()
        } else {

            // no net work available error message
            showNoNetworkConnection()

        }
    }

    private fun showNoNetworkConnection() {

        // redirect to no network fragment
        findNavController().navigate(R.id.action_trendingRepositoryFragment_to_noNetworkConnectionFragment)
    }

    private fun showLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.swipeToRefresh.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)

        // setup search view
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        // search vie width
        searchView.maxWidth = Int.MAX_VALUE

        // search keyboard Ime action
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        // Text watcher
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            View.OnFocusChangeListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchString: String?): Boolean {

                if (searchString!!.isNotEmpty()) {
                    binding.swipeToRefresh.isEnabled = false
                    binding.swipeToRefresh.isSelected = false
                } else {
                    binding.swipeToRefresh.isEnabled = true
                    binding.swipeToRefresh.isSelected = true
                }

                filter.filter(searchString)

                return false
            }

            override fun onFocusChange(v: View?, hasFocus: Boolean) {
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun setupObserver() {
        responseDataObserver = Observer {


            it?.let {
                /*
                * Check observer has data
                *
                * Yes:
                * 1. Insert it into local database
                * 2. Show content
                * 3. update recycler view
                * 4. show data success message
                *
                * No:
                * 1. Show failure message
                * 2. Stop refreshing and progressbar
                * */

                if (!it.isNullOrEmpty()) {

                    // insert new data into local database
                    viewModel.insertTrendingRepositoryInLocalDatabase(it)

                    // show message success on difference
                    if (binding.swipeToRefresh.isRefreshing)
                        showRefreshedMessage(getString(R.string.data_update_success))

                    // update view with new data
                    val localData =
                        viewModel.getAllDetailsFromLocal() as ArrayList<TrendingRepositoryResponseData>
                    updateRecyclerView(localData)

                    // show content
                    showContent()
                } else {

                    // if list is empty or null
                    showRefreshedMessage(getString(R.string.data_update_failed))

                    // stop refreshing and show swipe layout
                    if (swipe_to_refresh.isRefreshing)
                        swipe_to_refresh.isRefreshing = false

                    // stop progress bar
                    binding.progressBar.visibility = View.GONE
                }
            }

            // check for null values
            it ?: run {
                swipe_to_refresh.isRefreshing = false
                showRefreshedMessage(getString(R.string.data_update_failed))
            }
        }

        viewModel.responseRepositoryData.observe(viewLifecycleOwner, responseDataObserver)
    }

    private fun showRefreshedMessage(message: String) {
        Toast.makeText(
            requireActivity(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStop() {
        super.onStop()
        viewModel.responseRepositoryData.removeObservers(viewLifecycleOwner)
    }

    private fun showContent() {
        // hide error message
        binding.errorStateText.visibility = View.GONE

        // stop refreshing and show swipe layout
        if (swipe_to_refresh.isRefreshing)
            swipe_to_refresh.isRefreshing = false

        // stop progress bar
        binding.progressBar.visibility = View.GONE

        if (binding.swipeToRefresh.visibility == View.GONE) {
            binding.swipeToRefresh.visibility = View.VISIBLE
        }
    }

    private fun updateRecyclerView(localData: ArrayList<TrendingRepositoryResponseData>) {

        // show option only when data is available
        setHasOptionsMenu(true)

        adapter.updateList(localData)
    }



    // filterable for search view

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val responseRepositoryAllData = viewModel.getAllDetailsFromLocal()
                val filterList = ArrayList<TrendingRepositoryResponseData>()

                // if search string is empty show all items in array
                if (constraint == null || constraint.isEmpty()) {
                    filterList.addAll(responseRepositoryAllData)
                } else {

                    // if search string is not empty show filtered items
                    val searchString = constraint.toString().toLowerCase(Locale.getDefault()).trim()

                    for (item: TrendingRepositoryResponseData in responseRepositoryAllData) {
                        if (item.name.toLowerCase(Locale.getDefault()).contains(searchString)) {
                            filterList.add(item)
                        }
                    }
                }

                val filterResult = FilterResults()
                filterResult.values = filterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                val result = results!!.values as ArrayList<TrendingRepositoryResponseData>

                // update results
                if (result.isEmpty()) {
                    binding.noResultFound.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                } else {
                    adapter.updateList(result)
                    binding.noResultFound.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }

            }

        }
    }


}
package com.hari.kotlinflowsandcoroutines.ui.search

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hari.kotlinflowsandcoroutines.R
import com.hari.kotlinflowsandcoroutines.api.GithubService
import com.hari.kotlinflowsandcoroutines.databinding.FragmentSearchBinding
import com.hari.kotlinflowsandcoroutines.ui.MainActivity
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


class SearchFragment : Fragment() {
    private lateinit var searchView: MaterialSearchView
    private lateinit var mBinding: FragmentSearchBinding

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this, factory).get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, null, false)
        searchView = (activity as MainActivity).findViewById(R.id.search_view)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.viewModel = viewModel
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        observeSearchQuery()

    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun setUpAdapter() {
        val adapter = RepoAdapter()
        mBinding.recyclerViewRepo.adapter = adapter

        viewModel.repo.observe(viewLifecycleOwner, Observer {
            if (it.status.isSuccessful())
                adapter.submitList(it.data?.repositories)
        })
    }

    private fun observeSearchQuery() {
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.query.value = newText
                return true
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
    }


    private val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SearchViewModel(
                SearchRepository(GithubService.getGithubService())
            ) as T
        }
    }
}

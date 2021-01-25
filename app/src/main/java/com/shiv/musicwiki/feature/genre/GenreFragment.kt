package com.shiv.musicwiki.feature.genre

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.shiv.musicwiki.R
import com.shiv.musicwiki.base.BaseFragment
import com.shiv.musicwiki.databinding.FragmentGenreBinding
import com.shiv.musicwiki.ext.showToast
import com.shiv.musicwiki.ext.toVisibility
import com.shiv.musicwiki.paging.pagingAdapter.ReposLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GenreFragment : BaseFragment(R.layout.fragment_genre) {
    @Inject
    lateinit var mGenreAdapter: GenreAdapter
    private val mGenreViewModel: GenreViewModel by viewModels()
    private lateinit var mBinding: FragmentGenreBinding
    private var job: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentGenreBinding.bind(view)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.onClickListener = this
        mBinding.mGenreViewModel = mGenreViewModel
        adapterCallback()
        setUpSwipeToRefresh()
        initGenreListAdapter()
        getGenres()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            mBinding.ivSelect.id -> {
                mGenreViewModel.isExpanded.value = !mGenreViewModel.isExpanded.value!!
            }
        }
    }

    private fun adapterCallback() {
        mGenreAdapter.onClick = { tag ->
            tag.name?.let {
                GenreFragmentDirections.actionGenreFragmentToGenreDetailsFragment(it).also {
                    findNavController().navigate(it)
                }
            }
        }
    }

    private fun setUpSwipeToRefresh() {
        mBinding.swipeRefreshLayout.setOnRefreshListener {
            mGenreAdapter.refresh()
            mBinding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initGenreListAdapter() {
        val gridLayoutManager = GridLayoutManager(activity, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = mGenreAdapter.getItemViewType(position)
                return if (viewType == GenreAdapter.TAG_VIEW_TYPE) 1
                else 3
            }
        }
        with(mBinding) {
            rvGenre.apply {
                this.layoutManager = gridLayoutManager
                this.setHasFixedSize(true)
                this.adapter = mGenreAdapter.withLoadStateFooter(
                    footer = ReposLoadStateAdapter { mGenreAdapter.retry() })
            }

            mGenreAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh !is LoadState.NotLoading) {
                    progressBar.visibility = toVisibility(loadState.refresh is LoadState.Loading)
                    btnRetry.visibility = toVisibility(loadState.refresh is LoadState.Error)
                } else {
                    progressBar.visibility = View.GONE
                    btnRetry.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = false
                    val errorState = when {
                        loadState.append is LoadState.Error -> {
                            loadState.append as LoadState.Error
                        }
                        loadState.prepend is LoadState.Error -> {
                            loadState.prepend as LoadState.Error
                        }
                        else -> {
                            null
                        }
                    }
                    errorState?.let {
                        showToast(it.error.toString())
                    }
                }
            }
        }
    }

    private fun getGenres() {
        job?.cancel()
        job = lifecycleScope.launch {
            mGenreViewModel.genreList.collectLatest {
                mGenreAdapter.submitData(it)
            }
        }
    }
}
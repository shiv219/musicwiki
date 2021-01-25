package com.shiv.musicwiki.feature.genreDetail.tabs.track

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.shiv.musicwiki.R
import com.shiv.musicwiki.base.BaseFragment
import com.shiv.musicwiki.databinding.FragmentTrackBinding
import com.shiv.musicwiki.ext.showToast
import com.shiv.musicwiki.feature.genre.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TrackListFragment : BaseFragment(R.layout.fragment_track) {

    private val mTrackViewModel: TrackViewModel by viewModels()
    private lateinit var mBinding: FragmentTrackBinding
    private var job: Job? = null

    @Inject
    lateinit var mTrackAdapter: TrackAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentTrackBinding.bind(view)
        mBinding.lifecycleOwner = viewLifecycleOwner
        adapterCallback()
        setUpSwipeToRefresh()
        initTrackListAdapter()
        getTrack()
    }

    private fun adapterCallback() {
        mTrackAdapter.onClick = { tag ->

        }
    }

    private fun setUpSwipeToRefresh() {
        mBinding.swipeRefreshLayout.setOnRefreshListener {
            mTrackAdapter.refresh()
            mBinding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initTrackListAdapter() {
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = mTrackAdapter.getViewType(position)
                return if (viewType == GenreAdapter.TAG_VIEW_TYPE) 1
                else 2
            }
        }
        with(mBinding) {
            rvGenre.apply {
                this.layoutManager = gridLayoutManager
                this.setHasFixedSize(true)
                this.adapter = mTrackAdapter.withLoadStateFooter(
                    footer = com.shiv.musicwiki.paging.pagingAdapter.ReposLoadStateAdapter { mTrackAdapter.retry() })
            }

            mTrackAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh !is LoadState.NotLoading) {
                    progressBar.visibility =
                        com.shiv.musicwiki.ext.toVisibility(loadState.refresh is LoadState.Loading)
                    btnRetry.visibility =
                        com.shiv.musicwiki.ext.toVisibility(loadState.refresh is LoadState.Error)
                } else {
                    progressBar.visibility = android.view.View.GONE
                    btnRetry.visibility = android.view.View.GONE
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

    private fun getTrack() {
        job?.cancel()
        job = lifecycleScope.launch {
            mTrackViewModel.tracks.collectLatest { mTrackAdapter.submitData(it) }
        }
    }
}
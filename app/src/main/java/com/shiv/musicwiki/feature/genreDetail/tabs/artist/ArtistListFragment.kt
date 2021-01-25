package com.shiv.musicwiki.feature.genreDetail.tabs.artist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.shiv.musicwiki.R
import com.shiv.musicwiki.base.BaseFragment
import com.shiv.musicwiki.databinding.FragmentArtistBinding
import com.shiv.musicwiki.ext.showToast
import com.shiv.musicwiki.feature.genre.GenreAdapter
import com.shiv.musicwiki.feature.genreDetail.GenreDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ArtistListFragment : BaseFragment(R.layout.fragment_artist) {
    private val mArtistViewModel: ArtistViewModel by viewModels()
    private lateinit var mBinding: FragmentArtistBinding
    private var job: Job? = null

    @Inject
    lateinit var mArtistAdapter: ArtistAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentArtistBinding.bind(view)
        mBinding.lifecycleOwner = viewLifecycleOwner
        adapterCallback()
        setUpSwipeToRefresh()
        initArtistListAdapter()
        getArtist()
    }

    private fun adapterCallback() {
        mArtistAdapter.onClick = { tag ->
            tag.name?.let {
                (parentFragment as GenreDetailsFragment).moveTo(it, 2)
            }
        }
    }

    private fun setUpSwipeToRefresh() {
        mBinding.swipeRefreshLayout.setOnRefreshListener {
            mArtistAdapter.refresh()
            mBinding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initArtistListAdapter() {
        val gridLayoutManager = GridLayoutManager(activity, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = mArtistAdapter.getViewType(position)
                return if (viewType == GenreAdapter.TAG_VIEW_TYPE) 1
                else 2
            }
        }
        with(mBinding) {
            rvGenre.apply {
                this.layoutManager = gridLayoutManager
                this.setHasFixedSize(true)
                this.adapter = mArtistAdapter.withLoadStateFooter(
                    footer = com.shiv.musicwiki.paging.pagingAdapter.ReposLoadStateAdapter { mArtistAdapter.retry() })
            }

            mArtistAdapter.addLoadStateListener { loadState ->
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

    private fun getArtist() {
        job?.cancel()
        job = lifecycleScope.launch {
            mArtistViewModel.artists.collectLatest { mArtistAdapter.submitData(it) }
        }
    }
}
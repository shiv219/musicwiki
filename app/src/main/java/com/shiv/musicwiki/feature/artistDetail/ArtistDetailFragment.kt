package com.shiv.musicwiki.feature.artistDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.shiv.musicwiki.R
import com.shiv.musicwiki.base.BaseFragment
import com.shiv.musicwiki.databinding.FragmentArtistDetailBinding
import com.shiv.musicwiki.ext.showToast
import com.shiv.musicwiki.ext.toAppError
import com.shiv.musicwiki.paging.pagingAdapter.ReposLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ArtistDetailFragment : BaseFragment(R.layout.fragment_artist_detail) {

    @Inject
    lateinit var mArtistAdapter: ArtistTagsAdapter

    @Inject
    lateinit var mArtistTopTrackAdapter: ArtistTopTrackAdapter

    @Inject
    lateinit var mArtistTopAlbumAdapter: ArtistTopAlbumAdapter

    private var trackJob: Job? = null
    private var albumJob: Job? = null
    private lateinit var mBinding: FragmentArtistDetailBinding
    private val mArtistDetailViewModel: ArtistDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentArtistDetailBinding.bind(view)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.mViewModel = mArtistDetailViewModel
        mBinding.onClickListener = this
        adapterCallback()
        initTagsRV()
        initTopTracks()
        initTopAlbum()
        lifecycleScope.launch {
            if (mArtistDetailViewModel.artistInfoResponse.value == null)
                getArtistInfo()
        }
        lifecycleScope.launch {
            getTopTracks()
        }
        lifecycleScope.launch {
            getTopAlbum()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            mBinding.ivBack.id -> {
                onBackPressed()
            }
        }
    }

    private fun adapterCallback() {
        mArtistAdapter.onClick = { tags ->
            tags.name?.let { tag ->
                ArtistDetailFragmentDirections.actionArtistDetailFragmentToGenreDetailsFragment(tag)
                    .also {
                        findNavController().navigate(it)
                    }
            }
        }

        mArtistTopAlbumAdapter.onClick = { tags ->
            tags.name?.let { tag ->
                ArtistDetailFragmentDirections.actionArtistDetailFragmentToAlbumDetailFragment(tag)
                    .also {
                        findNavController().navigate(it)
                    }
            }
        }
        mArtistTopTrackAdapter
            .onClick = { tags ->
            tags.name?.let { tag ->

            }
        }
    }

    private fun initTagsRV() {
        mBinding.rvGenre.adapter = mArtistAdapter
        mArtistDetailViewModel.artistInfoResponse.observe(viewLifecycleOwner, Observer {
            it.artist?.tags?.tag?.let { tags ->
                mArtistAdapter.submitList(tags)
            }
        })
    }

    private fun getArtistInfo() {
        lifecycleScope.launch {
            mArtistDetailViewModel.artistInfo.collect { state ->
                showLoading(state.isLoading)
                state.getErrorIfExists()?.toAppError()?.let {
                    onError(it)
                }
                state.getValueOrNull()?.let {
                    mArtistDetailViewModel.artistInfoResponse.value = it
                }
            }
        }
    }


    private fun initTopTracks() {
        with(mBinding) {
            rvTracks.apply {
                this.adapter = mArtistTopTrackAdapter.withLoadStateFooter(
                    footer = ReposLoadStateAdapter { mArtistTopTrackAdapter.retry() })
            }

            mArtistTopTrackAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh !is LoadState.NotLoading) {
//                    progressBar.visibility = toVisibility(loadState.refresh is LoadState.Loading)
//                    btnRetry.visibility = toVisibility(loadState.refresh is LoadState.Error)
                } else {
//                    progressBar.visibility = View.GONE
//                    btnRetry.visibility = View.GONE
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

    private fun initTopAlbum() {
        with(mBinding) {
            rvAlbum.apply {
                this.adapter = mArtistTopAlbumAdapter.withLoadStateFooter(
                    footer = ReposLoadStateAdapter { mArtistTopAlbumAdapter.retry() })
            }

            mArtistTopAlbumAdapter.addLoadStateListener { loadState ->
                if (loadState.source.refresh !is LoadState.NotLoading) {
//                    progressBar.visibility = toVisibility(loadState.refresh is LoadState.Loading)
//                    btnRetry.visibility = toVisibility(loadState.refresh is LoadState.Error)
                } else {
//                    progressBar.visibility = View.GONE
//                    btnRetry.visibility = View.GONE
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

    private fun getTopTracks() {
        trackJob?.cancel()
        trackJob = lifecycleScope.launch {
            mArtistDetailViewModel.artistTopTrack.collectLatest {
                mArtistTopTrackAdapter.submitData(it)
            }
        }
    }

    private fun getTopAlbum() {
        albumJob?.cancel()
        albumJob = lifecycleScope.launch {
            mArtistDetailViewModel.artistTopAlbum.collectLatest {
                mArtistTopAlbumAdapter.submitData(it)
            }
        }
    }

}
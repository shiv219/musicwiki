package com.shiv.musicwiki.feature.albumDetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shiv.musicwiki.R
import com.shiv.musicwiki.base.BaseFragment
import com.shiv.musicwiki.databinding.FragmentAlbumDetailBinding
import com.shiv.musicwiki.ext.showToast
import com.shiv.musicwiki.ext.toAppError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AlbumDetailFragment : BaseFragment(R.layout.fragment_album_detail) {

    @Inject
    lateinit var mAlbumTagsAdapter: AlbumTagsAdapter
    private lateinit var mBinding: FragmentAlbumDetailBinding
    private val mAlbumDetailViewModel: AlbumDetailViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentAlbumDetailBinding.bind(view)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.onClickListener = this
        mBinding.mViewModel = mAlbumDetailViewModel
        adapterCallback()
        initTagsRV()
        getAlbumInfo()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            mBinding.ivBack.id -> {
                onBackPressed()
            }
        }
    }

    private fun adapterCallback() {
        mAlbumTagsAdapter.onClick = { tag ->
            tag.name?.let { tag ->
                AlbumDetailFragmentDirections.actionAlbumDetailFragmentToGenreDetailsFragment(tag = tag)
                    .also {
                        findNavController().navigate(it)
                    }
            }
        }
    }

    private fun initTagsRV() {
        mBinding.rvGenre.adapter = mAlbumTagsAdapter
        mAlbumDetailViewModel.albumInfoResponse.observe(viewLifecycleOwner, Observer {
            it.album?.tags?.tag?.let { tags ->
                mAlbumTagsAdapter.submitList(tags)
            }
        })
    }

    private fun getAlbumInfo() {
        lifecycleScope.launch {
            mAlbumDetailViewModel.albumInfo.collect { state ->
                showLoading(state.isLoading)
                state.getErrorIfExists().toAppError()?.let {
                    onError(it)
                }
                state.getValueOrNull()?.let {
                    mAlbumDetailViewModel.albumInfoResponse.value = it
                    if(it.album==null) {
                        showToast(getString(R.string.album_error))
                    }
                }
            }
        }
    }
}
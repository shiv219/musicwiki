package com.shiv.musicwiki.feature.genreDetail

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.shiv.musicwiki.R
import com.shiv.musicwiki.base.BaseFragment
import com.shiv.musicwiki.databinding.FragmentGenreDetailBinding
import com.shiv.musicwiki.ext.toAppError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GenreDetailsFragment : BaseFragment(R.layout.fragment_genre_detail) {

    private val mGenreDetailViewModel: GenreDetailViewModel by viewModels()
    private lateinit var mBinding: FragmentGenreDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentGenreDetailBinding.bind(view)
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.onClickListener = this
        mBinding.mViewModel = mGenreDetailViewModel
        if(mGenreDetailViewModel.infoResponse.value==null)
        getTagInfo()
        setViewPager()
    }

    fun moveTo(name:String,fragmentType:Int){
        when(fragmentType){
            1->{
                GenreDetailsFragmentDirections.actionGenreDetailsFragmentToAlbumDetailFragment(name = name).also {
                    findNavController().navigate(it)
                }
            }
            2->{
                GenreDetailsFragmentDirections.actionGenreDetailsFragmentToArtistDetailFragment(name = name).also {
                    findNavController().navigate(it)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            mBinding.ivBack.id -> {
                onBackPressed()
            }
        }
    }

    private fun setViewPager() {
        mBinding.viewPager.adapter =
            StudentViewPagerAdapter(this, mGenreDetailViewModel.tagName ?: "")

        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            when (position) {
                  0->{
                      tab.text = getString(R.string.album)
                  }
                  1->{
                      tab.text = getString(R.string.artist)
                  }
                  else ->{
                      tab.text = getString(R.string.track)
                  }
            }

        }.attach()
    }

    private fun getTagInfo() {
        lifecycleScope.launch {
            mGenreDetailViewModel.tagInfo.collect { loadState ->
                showLoading(loadState.isLoading)
                loadState.getErrorIfExists().toAppError()?.let {
                    onError(it)
                }
                loadState.getValueOrNull()?.let {
                    mGenreDetailViewModel.infoResponse.value = it

                }
            }
        }
    }
}
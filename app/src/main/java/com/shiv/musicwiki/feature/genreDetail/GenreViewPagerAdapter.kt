package com.shiv.musicwiki.feature.genreDetail

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shiv.musicwiki.feature.genreDetail.tabs.album.AlbumListFragment
import com.shiv.musicwiki.feature.genreDetail.tabs.artist.ArtistListFragment
import com.shiv.musicwiki.feature.genreDetail.tabs.track.TrackListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class StudentViewPagerAdapter(fragment: Fragment, val name:String) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> {
                AlbumListFragment().also {
                    it.arguments = bundleOf("tag" to name)
                }
            }
            1 -> {
                ArtistListFragment().also {
                    it.arguments = bundleOf("tag" to name)
                }
            }
            else -> {
                TrackListFragment().also {
                    it.arguments = bundleOf("tag" to name)
                }
            }
        }
    }


}
package com.shiv.musicwiki.feature.genreDetail.tabs.artist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shiv.musicwiki.R
import com.shiv.musicwiki.databinding.ListItemArtistBinding
import com.shiv.musicwiki.feature.genreDetail.tabs.album.AlbumAdapter
import com.shiv.musicwiki.model.artist.Artist
import javax.inject.Inject

class ArtistAdapter @Inject constructor() :
    PagingDataAdapter<Artist, ArtistAdapter.ViewHolder>(diffCallback = diffCallback) {

    var onClick: ((tag: Artist) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_artist, parent, false
            )
        )
    }

    inner class ViewHolder(private val mBinding: ListItemArtistBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(data: Artist) {
            mBinding.artist = data
            Glide.with(mBinding.root.context).load(data.image?.get(1)?.text?:"").error(R.drawable.artist).into(mBinding.ivImage)
            mBinding.ivImage.setOnClickListener {
                onClick?.let { it1 -> it1(data) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun getViewType(position: Int): Int {
        return if (position == itemCount) {
            AlbumAdapter.NETWORK_VIEW_TYPE
        } else {
            AlbumAdapter.TAG_VIEW_TYPE
        }
    }
    companion object {
        const val NETWORK_VIEW_TYPE = 2
        const val TAG_VIEW_TYPE = 1
        val diffCallback = object : DiffUtil.ItemCallback<Artist>() {
            override fun areItemsTheSame(oldItem: Artist, newItem: Artist) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Artist, newItem: Artist) =
                oldItem == newItem
        }
    }
}
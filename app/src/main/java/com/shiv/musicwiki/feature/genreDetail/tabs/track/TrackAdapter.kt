package com.shiv.musicwiki.feature.genreDetail.tabs.track

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shiv.musicwiki.R
import com.shiv.musicwiki.databinding.ListItemTrackBinding
import com.shiv.musicwiki.feature.genreDetail.tabs.album.AlbumAdapter
import com.shiv.musicwiki.model.track.Track
import javax.inject.Inject

class TrackAdapter @Inject constructor() :
    PagingDataAdapter<Track, TrackAdapter.ViewHolder>(diffCallback = diffCallback) {

    var onClick: ((tag: Track) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_track, parent, false
            )
        )
    }

    inner class ViewHolder(private val mBinding: ListItemTrackBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(data: Track) {
            mBinding.track = data
            Glide.with(mBinding.root.context).load(data.image?.get(1)?.text?:"").error(R.drawable.track).into(mBinding.ivImage)
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
        val diffCallback = object : DiffUtil.ItemCallback<Track>() {
            override fun areItemsTheSame(oldItem: Track, newItem: Track) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Track, newItem: Track) =
                oldItem == newItem
        }
    }
}
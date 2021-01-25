package com.shiv.musicwiki.feature.artistDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shiv.musicwiki.R
import com.shiv.musicwiki.databinding.ListItemArtistTopAlbumBinding
import com.shiv.musicwiki.model.artist.ArtistTrack
import javax.inject.Inject

class ArtistTopTrackAdapter @Inject constructor() :
    PagingDataAdapter<ArtistTrack, ArtistTopTrackAdapter.ViewHolder>(diffCallback = diffCallback) {

    var onClick: ((tag: ArtistTrack) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_artist_top_album, parent, false
            )
        )
    }

    inner class ViewHolder(private val mBinding: ListItemArtistTopAlbumBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(data: ArtistTrack) {
            mBinding.tvTitle.text = data.name
            mBinding.tvSubtitle.text = data.artist?.name ?: ""
            Glide.with(mBinding.root.context).load(data.image?.get(1)?.text ?: "")
                .error(R.drawable.artist).into(mBinding.ivImage)
            mBinding.ivImage.setOnClickListener {
                onClick?.let { it1 -> it1(data) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ArtistTrack>() {
            override fun areItemsTheSame(oldItem: ArtistTrack, newItem: ArtistTrack) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: ArtistTrack, newItem: ArtistTrack) =
                oldItem == newItem
        }
    }
}
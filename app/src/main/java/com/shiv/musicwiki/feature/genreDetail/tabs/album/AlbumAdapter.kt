package com.shiv.musicwiki.feature.genreDetail.tabs.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shiv.musicwiki.R
import com.shiv.musicwiki.databinding.ListItemAlbumBinding
import com.shiv.musicwiki.model.album.Album
import javax.inject.Inject

class AlbumAdapter @Inject constructor() :
    PagingDataAdapter<Album, AlbumAdapter.ViewHolder>(diffCallback = diffCallback) {

    var onClick: ((tag: Album) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_album, parent, false
            )
        )
    }

    inner class ViewHolder(private val mBinding: ListItemAlbumBinding) :
        RecyclerView.ViewHolder(mBinding.root) {


        fun bind(data: Album) {
            mBinding.album = data
            mBinding.ivImage.setImageDrawable(null)
            Glide.with(mBinding.root.context).load(data.image?.get(1)?.text ?: "")
                .error(R.drawable.album).into(mBinding.ivImage)
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
            NETWORK_VIEW_TYPE
        } else {
            TAG_VIEW_TYPE
        }
    }

    companion object {
        const val NETWORK_VIEW_TYPE = 2
        const val TAG_VIEW_TYPE = 1
        val diffCallback = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Album, newItem: Album) =
                oldItem == newItem
        }
    }
}
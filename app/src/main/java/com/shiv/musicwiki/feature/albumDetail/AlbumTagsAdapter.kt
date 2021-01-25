package com.shiv.musicwiki.feature.albumDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shiv.musicwiki.R
import com.shiv.musicwiki.databinding.LayoutHorizontalChipBinding
import com.shiv.musicwiki.model.album.Tag
import javax.inject.Inject

class AlbumTagsAdapter @Inject constructor() :
    ListAdapter<Tag, AlbumTagsAdapter.ViewHolder>(diffCallback) {

    var onClick: ((tag: Tag) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_horizontal_chip, parent, false
            )
        )
    }

    inner class ViewHolder(private val mBinding: LayoutHorizontalChipBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(data: Tag) {
            mBinding.genreName = data.name
            mBinding.chip.setOnClickListener {
                onClick?.let { it1 -> it1(data) }
            }
        }
    }

    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<Tag>() {
            override fun areItemsTheSame(oldItem: Tag, newItem: Tag) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Tag, newItem: Tag) =
                oldItem == newItem
        }
    }
}
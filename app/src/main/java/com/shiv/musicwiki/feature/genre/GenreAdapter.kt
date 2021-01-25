package com.shiv.musicwiki.feature.genre

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shiv.musicwiki.R
import com.shiv.musicwiki.databinding.LayoutChipBinding
import com.shiv.musicwiki.model.genre.Tag
import javax.inject.Inject

class GenreAdapter @Inject constructor() :
    PagingDataAdapter<Tag, GenreAdapter.ViewHolder>(diffCallback = diffCallback ) {

    var onClick: ((tag : Tag) -> Unit)?=null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_chip, parent, false
            )
        )
    }

    inner class ViewHolder(private val mBinding: LayoutChipBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(data: Tag) {
            mBinding.genreName = data.name
            mBinding.chip.setOnClickListener {
                onClick?.let { it1 -> it1(data) }

            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount){
            NETWORK_VIEW_TYPE
        }else {
            TAG_VIEW_TYPE
        }
    }

    companion object {
        const val NETWORK_VIEW_TYPE = 2
        const val TAG_VIEW_TYPE = 1
        val diffCallback = object : DiffUtil.ItemCallback<Tag>() {
            override fun areItemsTheSame(oldItem: Tag, newItem: Tag) =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Tag, newItem: Tag) =
                oldItem == newItem
        }
    }
}
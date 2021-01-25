package com.shiv.musicwiki.bindingAdapter

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.shiv.musicwiki.R

@BindingAdapter("loadAlbum")
fun ImageView.loadAlbum(imageString: String?) {

    Glide.with(context).load(imageString).placeholder(R.drawable.album).error(R.drawable.album)
        .into(this)
}

@BindingAdapter("loadArtist")
fun ImageView.loadArtist(imageString: String?) {
    Glide.with(context).load(imageString).placeholder(R.drawable.artist).error(R.drawable.artist)
        .into(this)

}

@BindingAdapter("loadTrack")
fun ImageView.loadTrack(imageString: String?) {

    Glide.with(context).load(imageString).placeholder(R.drawable.track).error(R.drawable.track)
        .into(this)

}

@BindingAdapter("htmlText")
fun TextView.htmlText(htmlText: String?) {
    htmlText?.let {
        this.text = Html.fromHtml(it)
    }
}
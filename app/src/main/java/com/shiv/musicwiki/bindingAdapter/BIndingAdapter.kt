package com.shiv.musicwiki.bindingAdapter

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.shiv.musicwiki.R

@BindingAdapter("loadAlbum")
fun ImageView.loadAlbum(imageString: String?) {
//    if(imageString.isNullOrEmpty()){
//    this.setImageResource(R.drawable.album)
//}else
    Glide.with(context).load(imageString).placeholder(R.drawable.album).error(R.drawable.album).into(this)
}
@BindingAdapter("loadArtist")
fun ImageView.loadArtist(imageString: String?) {
    if(imageString.isNullOrEmpty()){
    this.setImageResource(R.drawable.artist)
}else
    Glide.with(context).load(imageString).into(this)
}
@BindingAdapter("loadTrack")
fun ImageView.loadTrack(imageString: String?) {
    if(imageString.isNullOrEmpty()){
    this.setImageResource(R.drawable.track)
}else
    Glide.with(context).load(imageString).into(this)
}

@BindingAdapter("htmlText")
fun TextView.htmlText(htmlText:String?){
    htmlText?.let {
        this.text = Html.fromHtml(it)
    }
}
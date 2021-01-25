package com.shiv.musicwiki.model.artist
import com.google.gson.annotations.SerializedName


data class ArtistTopTrackResposne(
    @SerializedName("toptracks")
    val toptracks: ArtistToptracks?
)

data class ArtistToptracks(
    @SerializedName("@attr")
    val attr: ArtistTrackAttr?,
    @SerializedName("track")
    val track: List<ArtistTrack>?
)

data class ArtistTrackAttr(
    @SerializedName("artist")
    val artist: String?,
    @SerializedName("page")
    val page: String?,
    @SerializedName("perPage")
    val perPage: String?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("totalPages")
    val totalPages: String?
)

data class ArtistTrack(
    @SerializedName("artist")
    val artist: TrackArtist?,
    @SerializedName("@attr")
    val attr: AttrX?,
    @SerializedName("image")
    val image: List<Image>?,
    @SerializedName("listeners")
    val listeners: String?,
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("playcount")
    val playcount: String?,
    @SerializedName("streamable")
    val streamable: String?,
    @SerializedName("url")
    val url: String?
)

data class TrackArtist(
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

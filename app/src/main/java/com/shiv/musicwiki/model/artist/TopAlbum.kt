package com.shiv.musicwiki.model.artist
import com.google.gson.annotations.SerializedName


data class ArtistTopAlbumResponse(
    @SerializedName("topalbums")
    val topalbums: Topalbums?
)

data class Topalbums(
    @SerializedName("album")
    val album: List<ArtistTopAlbum>?,
    @SerializedName("@attr")
    val attr: ArtistAttr?
)

data class ArtistTopAlbum(
    @SerializedName("artist")
    val artist: ArtistX?,
    @SerializedName("image")
    val image: List<Image>?,
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("playcount")
    val playcount: Int?,
    @SerializedName("url")
    val url: String?
)

data class ArtistAttr(
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

data class ArtistX(
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

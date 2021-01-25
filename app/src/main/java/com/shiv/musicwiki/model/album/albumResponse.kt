package com.shiv.musicwiki.model.album
import com.google.gson.annotations.SerializedName


data class AlbumResponse(
    @SerializedName("albums")
    val albums: Albums?
)

data class Albums(
    @SerializedName("album")
    val album: List<Album>?,
    @SerializedName("@attr")
    val attr: AttrX?
)

data class Album(
    @SerializedName("artist")
    val artist: AlbumArtist?,
    @SerializedName("@attr")
    val attr: Attr?,
    @SerializedName("image")
    val image: List<Image>?,
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class AttrX(
    @SerializedName("page")
    val page: String?,
    @SerializedName("perPage")
    val perPage: String?,
    @SerializedName("tag")
    val tag: String?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("totalPages")
    val totalPages: String?
)

data class AlbumArtist(
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class Attr(
    @SerializedName("rank")
    val rank: String?
)

data class Image(
    @SerializedName("size")
    val size: String?,
    @SerializedName("#text")
    val text: String?
)

data class AlbumInfoResponse(
    @SerializedName("album")
    val album: AlbumInfo?
)

data class AlbumInfo(
    @SerializedName("artist")
    val artist: String?,
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
    @SerializedName("tags")
    val tags: Tags?,
    @SerializedName("tracks")
    val tracks: Tracks?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("wiki")
    val wiki: Wiki?
)
//
//data class Image(
//    @SerializedName("size")
//    val size: String?,
//    @SerializedName("#text")
//    val text: String?
//)

data class Tags(
    @SerializedName("tag")
    val tag: List<Tag>?
)

data class Tracks(
    @SerializedName("track")
    val track: List<Track>?
)

data class Wiki(
    @SerializedName("content")
    val content: String?,
    @SerializedName("published")
    val published: String?,
    @SerializedName("summary")
    val summary: String?
)

data class Tag(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class Track(
    @SerializedName("artist")
    val artist: Artist?,
    @SerializedName("@attr")
    val attr: Attr?,
    @SerializedName("duration")
    val duration: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("streamable")
    val streamable: Streamable?,
    @SerializedName("url")
    val url: String?
)

data class Artist(
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

//data class Attr(
//    @SerializedName("rank")
//    val rank: String?
//)
//
data class Streamable(
    @SerializedName("fulltrack")
    val fulltrack: String?,
    @SerializedName("#text")
    val text: String?
)
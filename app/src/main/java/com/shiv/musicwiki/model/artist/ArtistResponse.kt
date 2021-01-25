package com.shiv.musicwiki.model.artist
import com.google.gson.annotations.SerializedName


data class ArtistResponse(
    @SerializedName("topartists")
    val topartists: Topartists?
)

data class Topartists(
    @SerializedName("artist")
    val artist: List<Artist>?,
    @SerializedName("@attr")
    val attr: AttrX?
)

data class Artist(
    @SerializedName("@attr")
    val attr: Attr?,
    @SerializedName("bio")
    val bio: Bio?,
    @SerializedName("image")
    val image: List<Image>?,
    @SerializedName("mbid")
    val mbid: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("ontour")
    val ontour: String?,
    @SerializedName("similar")
    val similar: Similar?,
    @SerializedName("stats")
    val stats: Stats?,
    @SerializedName("streamable")
    val streamable: String?,
    @SerializedName("tags")
    val tags: Tags?,
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

data class ArtistInfoResponse(
    @SerializedName("artist")
    val artist: Artist?
)

data class Bio(
    @SerializedName("content")
    val content: String?,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("published")
    val published: String?,
    @SerializedName("summary")
    val summary: String?
)

data class Similar(
    @SerializedName("artist")
    val similarArtist: List<SimilarArtist>?
)

data class Stats(
    @SerializedName("listeners")
    val listeners: String?,
    @SerializedName("playcount")
    val playcount: String?
)

data class Tags(
    @SerializedName("tag")
    val tag: List<Tag>?
)

data class Links(
    @SerializedName("link")
    val link: Link?
)

data class Link(
    @SerializedName("href")
    val href: String?,
    @SerializedName("rel")
    val rel: String?,
    @SerializedName("#text")
    val text: String?
)

data class SimilarArtist(
    @SerializedName("image")
    val image: List<ImageX>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)

data class ImageX(
    @SerializedName("size")
    val size: String?,
    @SerializedName("#text")
    val text: String?
)

data class Tag(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)
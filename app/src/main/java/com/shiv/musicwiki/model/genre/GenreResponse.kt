package com.shiv.musicwiki.model.genre
import com.google.gson.annotations.SerializedName
import com.shiv.musicwiki.model.genreInfo.Wiki


data class GenreResposne(
    @SerializedName("tags")
    val tags: Tags?
)

data class Tags(
    @SerializedName("@attr")
    val attr: Attr?,
    @SerializedName("tag")
    val tag: List<Tag>?
)

data class Attr(
    @SerializedName("page")
    val page: String?,
    @SerializedName("perPage")
    val perPage: String?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("totalPages")
    val totalPages: String?
)

data class Tag(
    @SerializedName("name")
    val name: String?,
    @SerializedName("reach")
    val reach: String?,
    @SerializedName("streamable")
    val streamable: String?,
    @SerializedName("taggings")
    val taggings: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("wiki")
    val wiki: Wiki?
)

class Wiki(
)
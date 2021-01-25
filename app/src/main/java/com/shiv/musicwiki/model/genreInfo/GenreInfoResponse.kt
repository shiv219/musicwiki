package com.shiv.musicwiki.model.genreInfo
import com.google.gson.annotations.SerializedName
import com.shiv.musicwiki.model.genre.Tag
import com.shiv.musicwiki.model.genre.Wiki


data class GenreInfoResponse(
    @SerializedName("tag")
    val tag: Tag?
)

data class Tag(
    @SerializedName("name")
    val name: String?,
    @SerializedName("reach")
    val reach: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("wiki")
    val wiki: Wiki?
)

data class Wiki(
    @SerializedName("content")
    val content: String?,
    @SerializedName("summary")
    val summary: String?
)
package com.shiv.musicwiki.data.network

import com.shiv.musicwiki.BuildConfig
import com.shiv.musicwiki.model.album.AlbumInfoResponse
import com.shiv.musicwiki.model.album.AlbumResponse
import com.shiv.musicwiki.model.artist.ArtistInfoResponse
import com.shiv.musicwiki.model.artist.ArtistResponse
import com.shiv.musicwiki.model.artist.ArtistTopAlbumResponse
import com.shiv.musicwiki.model.artist.ArtistTopTrackResposne
import com.shiv.musicwiki.model.genre.GenreResposne
import com.shiv.musicwiki.model.genreInfo.GenreInfoResponse
import com.shiv.musicwiki.model.track.TrackResponse
import com.shiv.musicwiki.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("2.0/")
    suspend fun getGenreList(
        @Query("method") method: String = Constants.gettoptags,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = Constants.json,
        @Query("page") pageCount: Int
    ): GenreResposne

    @GET("2.0/")
    fun getTagInfo(
        @Query("method") method: String = Constants.getInfo,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = Constants.json,
        @Query("tag") tag: String
    ): Call<GenreInfoResponse>

    @GET("2.0/")
    suspend fun getAlbum(
        @Query("method") method: String = Constants.gettopalbums,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = Constants.json,
        @Query("tag") tag: String,
        @Query("page") pageCount: Int,
        @Query("limit") limit: Int
    ): AlbumResponse

    @GET("2.0/")
    suspend fun getArtist(
        @Query("method") method: String = Constants.gettopartists,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = Constants.json,
        @Query("tag") tag: String,
        @Query("page") pageCount: Int
    ): ArtistResponse

    @GET("2.0/")
    suspend fun getTrack(
        @Query("method") method: String = Constants.gettoptracks,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = Constants.json,
        @Query("tag") tag: String,
        @Query("page") pageCount: Int
    ): TrackResponse

    @GET("2.0/")
    fun getAlbumInfo(
        @Query("method") method: String = Constants.albumInfo,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = Constants.json,
        @Query("album") album: String
    ): Call<AlbumInfoResponse>

    @GET("2.0/")
    fun getArtistInfo(
        @Query("method") method: String = Constants.artistInfo,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = Constants.json,
        @Query("artist") artist: String
    ): Call<ArtistInfoResponse>

    @GET("2.0/")
    suspend fun getArtistTopAlbum(
        @Query("method") method: String = Constants.artistTopalbums,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = Constants.json,
        @Query("artist") artist: String,
        @Query("page") pageCount: Int
    ): ArtistTopAlbumResponse

    @GET("2.0/")
    suspend fun getArtistTopTrack(
        @Query("method") method: String = Constants.artistTtoptracks,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("format") format: String = Constants.json,
        @Query("artist") artist: String,
        @Query("page") pageCount: Int
    ): ArtistTopTrackResposne

}

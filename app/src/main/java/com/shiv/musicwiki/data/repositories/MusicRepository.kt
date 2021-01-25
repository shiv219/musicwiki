package com.shiv.musicwiki.data.repositories

import androidx.paging.PagingData
import com.shiv.musicwiki.model.album.Album
import com.shiv.musicwiki.model.album.AlbumInfoResponse
import com.shiv.musicwiki.model.artist.*
import com.shiv.musicwiki.model.genre.Tag
import com.shiv.musicwiki.model.genreInfo.GenreInfoResponse
import com.shiv.musicwiki.model.track.Track
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    fun getGenreList(): Flow<PagingData<Tag>>
    fun getTagInfo(name: String): Flow<GenreInfoResponse>
    fun getAlbums(name: String): Flow<PagingData<Album>>
    fun getArtist(name: String): Flow<PagingData<Artist>>
    fun getTracks(name: String): Flow<PagingData<Track>>
    fun getAlbumInfo(name: String): Flow<AlbumInfoResponse>
    fun getArtistInfo(name: String): Flow<ArtistInfoResponse>
    fun getArtistTopAlbum(name:String): Flow<PagingData<ArtistTopAlbum>>
    fun getArtistTopTracks(name: String): Flow<PagingData<ArtistTrack>>
}
package com.shiv.musicwiki.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.filter
import com.shiv.musicwiki.data.network.ApiServices
import com.shiv.musicwiki.ext.bodyOrThrow
import com.shiv.musicwiki.model.album.AlbumInfoResponse
import com.shiv.musicwiki.paging.pagingSource.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MusicRepositoryImp @Inject constructor(private val apiServices: ApiServices) :
    MusicRepository {
    override fun getGenreList() = Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
        pagingSourceFactory = {
            GenrePagingSource(apiServices)
        }).flow


    override fun getTagInfo(name: String) = flow {
        emit(apiServices.getTagInfo(tag = name).execute().bodyOrThrow())
    }

    override fun getAlbums(name: String) =
        Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                AlbumPagingSource(service = apiServices, name = name)
            }).flow


    override fun getArtist(name: String) =
        Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                ArtistPagingSource(service = apiServices, name = name)
            }).flow.map { value -> value.filter { it.name != null } }


    override fun getTracks(name: String) =
        Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                TrackPagingSource(service = apiServices, name = name)
            }).flow.map { value -> value.filter { it.name != null } }


    override fun getAlbumInfo(name: String): Flow<AlbumInfoResponse> = flow {
        emit(apiServices.getAlbumInfo(album = name).execute().bodyOrThrow())
    }

    override fun getArtistInfo(name: String) = flow {
        emit(apiServices.getArtistInfo(artist = name).execute().bodyOrThrow())
    }

    override fun getArtistTopAlbum(name: String) =
        Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                ArtistTopAlbumPagingSource(service = apiServices, name = name)
            }).flow.map { value -> value.filter { it.name != null } }


    override fun getArtistTopTracks(name: String) =
        Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                ArtistTopTrackPagingSource(service = apiServices, name = name)
            }).flow.map { value -> value.filter { it.name != null } }

    companion object {
        private const val NETWORK_PAGE_SIZE = 15
    }
}
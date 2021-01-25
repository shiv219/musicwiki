package com.shiv.musicwiki.paging.pagingSource

import androidx.paging.PagingSource
import com.shiv.musicwiki.data.network.ApiServices
import com.shiv.musicwiki.model.artist.ArtistTrack
import retrofit2.HttpException

/**
 * PagingSource class to handle paging calls
 * use this class without Room i.e network only
 */
private const val STARTING_PAGE_INDEX = 1

class ArtistTopTrackPagingSource(
    private val service: ApiServices,
    private val name: String
) : PagingSource<Int, ArtistTrack>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtistTrack> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getArtistTopTrack(
                artist = name,
                pageCount = position
            )
            LoadResult.Page(
                data = response.toptracks?.track!!,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.toptracks.track.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
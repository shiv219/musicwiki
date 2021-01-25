package com.shiv.musicwiki.paging.pagingSource

import androidx.paging.PagingSource
import com.shiv.musicwiki.data.network.ApiServices
import com.shiv.musicwiki.model.track.Track
import retrofit2.HttpException

/**
 * PagingSource class to handle paging calls
 * use this class without Room i.e network only
 */
private const val STARTING_PAGE_INDEX = 1

class TrackPagingSource(
    private val service: ApiServices,
    private val name: String
) : PagingSource<Int, Track>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Track> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getTrack(
                tag = name,
                pageCount = position
            )
            LoadResult.Page(
                data = response.tracks?.track!!,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.tracks.track.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
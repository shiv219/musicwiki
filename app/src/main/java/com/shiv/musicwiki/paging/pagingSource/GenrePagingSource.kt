package com.shiv.musicwiki.paging.pagingSource

import androidx.paging.PagingSource
import com.shiv.musicwiki.data.network.ApiServices
import com.shiv.musicwiki.model.genre.Tag
import retrofit2.HttpException

/**
 * PagingSource class to handle paging calls
 * use this class without Room i.e network only
 */
private const val STARTING_PAGE_INDEX = 1

class GenrePagingSource(
    private val service: ApiServices
) : PagingSource<Int, Tag>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tag> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getGenreList(
                pageCount = position
            )
            LoadResult.Page(
                data = response.tags?.tag!!,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.tags.tag.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
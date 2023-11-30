package com.mbj.composepaging.data.remote.network.api.rick_morty.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mbj.composepaging.data.remote.model.RickMorty
import com.mbj.composepaging.data.remote.network.api.rick_morty.RickMortyApiClient
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RickMortyPagingDataSource @Inject constructor(
    private val rickMortyApiService: RickMortyApiClient
) : PagingSource<Int, RickMorty>() {
    override fun getRefreshKey(state: PagingState<Int, RickMorty>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RickMorty> {
        val start = params.key ?: 1
        return try {
            val response = rickMortyApiService.getAllCharacters(start)
            val data: List<RickMorty> = response.body()?.results ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (start == 1) null else start - 1,
                nextKey = if (data.isEmpty()) null else start + params.loadSize
            )
        } catch (e: IOException)  {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

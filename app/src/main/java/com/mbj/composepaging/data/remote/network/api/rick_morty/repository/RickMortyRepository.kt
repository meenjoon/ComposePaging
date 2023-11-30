package com.mbj.composepaging.data.remote.network.api.rick_morty.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mbj.composepaging.data.remote.model.RickMorty
import com.mbj.composepaging.data.remote.network.api.rick_morty.RickMortyApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickMortyRepository @Inject constructor(
    private val rickMortyPagingDataSource: RickMortyPagingDataSource
) : RickMortyApi {
    override fun getAllCharacters(): Flow<PagingData<RickMorty>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { rickMortyPagingDataSource }
        ).flow
    }
}

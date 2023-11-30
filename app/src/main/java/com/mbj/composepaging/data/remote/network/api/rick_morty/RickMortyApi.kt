package com.mbj.composepaging.data.remote.network.api.rick_morty

import androidx.paging.PagingData
import com.mbj.composepaging.data.remote.model.RickMorty
import kotlinx.coroutines.flow.Flow

interface RickMortyApi {

    fun getAllCharacters(): Flow<PagingData<RickMorty>>
}

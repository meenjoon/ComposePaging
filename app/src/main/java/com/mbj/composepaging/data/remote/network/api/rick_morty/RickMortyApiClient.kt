package com.mbj.composepaging.data.remote.network.api.rick_morty

import com.mbj.composepaging.data.remote.model.RickMorties
import com.mbj.composepaging.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickMortyApiClient {

    @GET(Constants.END_PONT)
    suspend fun getAllCharacters(
        @Query("page") page: Int,
    ): Response<RickMorties>
}

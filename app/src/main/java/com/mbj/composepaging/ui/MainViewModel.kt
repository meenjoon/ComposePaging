package com.mbj.composepaging.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mbj.composepaging.data.remote.model.RickMorty
import com.mbj.composepaging.data.remote.network.api.rick_morty.RickMortyApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    rickMortyApi: RickMortyApi
) : ViewModel() {

    val rickMorty: Flow<PagingData<RickMorty>> = rickMortyApi.getAllCharacters().cachedIn(viewModelScope)
}

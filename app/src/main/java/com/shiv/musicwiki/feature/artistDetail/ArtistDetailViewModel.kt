package com.shiv.musicwiki.feature.artistDetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shiv.musicwiki.data.repositories.MusicRepository
import com.shiv.musicwiki.ext.toLoadingState
import com.shiv.musicwiki.model.artist.ArtistInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn


@ExperimentalCoroutinesApi
class ArtistDetailViewModel @ViewModelInject constructor(
    musicRepository: MusicRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val artistName = savedStateHandle.get<String>("name")

    val artistInfoResponse = MutableLiveData<ArtistInfoResponse>()

    val artistInfo =
        musicRepository.getArtistInfo(artistName ?: "").toLoadingState().flowOn(Dispatchers.IO)

    val artistTopTrack = musicRepository.getArtistTopTracks(artistName?:"").cachedIn(viewModelScope)
    val artistTopAlbum = musicRepository.getArtistTopAlbum(artistName?:"").cachedIn(viewModelScope)

}
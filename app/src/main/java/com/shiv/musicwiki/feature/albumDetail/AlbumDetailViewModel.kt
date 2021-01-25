package com.shiv.musicwiki.feature.albumDetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.shiv.musicwiki.data.repositories.MusicRepository
import com.shiv.musicwiki.ext.toLoadingState
import com.shiv.musicwiki.model.album.AlbumInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn


class AlbumDetailViewModel @ViewModelInject constructor(
    musicRepository: MusicRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val albumName = savedStateHandle.get<String>("name")

    val albumInfoResponse = MutableLiveData<AlbumInfoResponse>()

    val albumInfo = musicRepository.getAlbumInfo(name = albumName?:"").toLoadingState().flowOn(Dispatchers.IO)
}
package com.shiv.musicwiki.feature.genreDetail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.shiv.musicwiki.data.repositories.MusicRepository
import com.shiv.musicwiki.ext.toLoadingState
import com.shiv.musicwiki.model.genreInfo.GenreInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn

@ExperimentalCoroutinesApi
class GenreDetailViewModel @ViewModelInject constructor(
    musicRepository: MusicRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    var tagName = savedStateHandle.get<String>("tag")
    var infoResponse = MutableLiveData<GenreInfoResponse>()
    val tagInfo = musicRepository.getTagInfo(tagName ?: "").toLoadingState().flowOn(Dispatchers.IO)

}
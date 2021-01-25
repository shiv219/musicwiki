package com.shiv.musicwiki.feature.genreDetail.tabs.album

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shiv.musicwiki.data.repositories.MusicRepository

class AlbumViewModel @ViewModelInject constructor(
    musicRepository: MusicRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    var tagName = savedStateHandle.get<String>("tag")
    val album = musicRepository.getAlbums(tagName ?: "").cachedIn(viewModelScope)
}
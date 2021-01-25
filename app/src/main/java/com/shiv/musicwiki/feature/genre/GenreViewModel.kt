package com.shiv.musicwiki.feature.genre

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shiv.musicwiki.data.repositories.MusicRepository
import kotlinx.coroutines.flow.collect

class GenreViewModel @ViewModelInject constructor(musicRepository: MusicRepository) : ViewModel() {
    var isExpanded = MutableLiveData(false)
    val genreList = musicRepository.getGenreList().cachedIn(viewModelScope)
}
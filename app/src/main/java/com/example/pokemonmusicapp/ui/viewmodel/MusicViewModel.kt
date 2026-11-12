package com.example.pokemonmusicapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonmusicapp.data.model.Album
import com.example.pokemonmusicapp.data.model.Artist
import com.example.pokemonmusicapp.data.model.Track
import com.example.pokemonmusicapp.data.repository.AudioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface MusicArtistState {
    object Idle : MusicArtistState
    object Loading : MusicArtistState
    data class Success(val artist: Artist?, val albums: List<Album>) : MusicArtistState
    data class Error(val message: String) : MusicArtistState
}

sealed interface AlbumDetailState {
    object Loading : AlbumDetailState
    data class Success(val album: Album?, val tracks: List<Track>) : AlbumDetailState
    data class Error(val message: String) : AlbumDetailState
}

class MusicViewModel(private val repository: AudioRepository) : ViewModel() {
    private val _artistState = MutableStateFlow<MusicArtistState>(MusicArtistState.Idle)
    val artistState: StateFlow<MusicArtistState> = _artistState

    private val _albumState = MutableStateFlow<AlbumDetailState>(AlbumDetailState.Loading)
    val albumState: StateFlow<AlbumDetailState> = _albumState

    fun searchMusicArtist(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            _artistState.value = MusicArtistState.Loading
            try {
                val artist = repository.getArtist(name)
                val albums = repository.getAlbums(name)
                _artistState.value = MusicArtistState.Success(artist, albums)
            } catch (e: Exception) {
                _artistState.value = MusicArtistState.Error(e.localizedMessage ?: "Gagal memuat data artis.")
            }
        }
    }

    fun loadAlbumDetail(albumId: String) {
        viewModelScope.launch {
            _albumState.value = AlbumDetailState.Loading
            try {
                val album = repository.getAlbumDetail(albumId)
                val tracks = repository.getTracks(albumId)
                _albumState.value = AlbumDetailState.Success(album, tracks)
            } catch (e: Exception) {
                _albumState.value = AlbumDetailState.Error(e.localizedMessage ?: "Gagal memuat detail album.")
            }
        }
    }
}

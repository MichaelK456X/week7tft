package com.example.pokemonmusicapp.data.repository

import com.example.pokemonmusicapp.data.model.Album
import com.example.pokemonmusicapp.data.model.Artist
import com.example.pokemonmusicapp.data.model.Track
import com.example.pokemonmusicapp.data.service.AudioApiService

class AudioRepository(private val apiService: AudioApiService) {
    suspend fun getArtist(name: String): Artist? {
        return apiService.searchArtist(name).artists?.firstOrNull()
    }

    suspend fun getAlbums(artistName: String): List<Album> {
        return apiService.searchAlbums(artistName).album ?: emptyList()
    }

    suspend fun getAlbumDetail(albumId: String): Album? {
        return apiService.getAlbumDetail(albumId).album?.firstOrNull()
    }

    suspend fun getTracks(albumId: String): List<Track> {
        return apiService.getAlbumTracks(albumId).track ?: emptyList()
    }
}

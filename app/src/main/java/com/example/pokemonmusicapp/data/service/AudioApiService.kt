package com.example.pokemonmusicapp.data.service

import com.example.pokemonmusicapp.data.model.AlbumResponse
import com.example.pokemonmusicapp.data.model.ArtistResponse
import com.example.pokemonmusicapp.data.model.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AudioApiService {
    @GET("search.php")
    suspend fun searchArtist(@Query("s") artistName: String): ArtistResponse

    @GET("searchalbum.php")
    suspend fun searchAlbums(@Query("s") artistName: String): AlbumResponse

    @GET("album.php")
    suspend fun getAlbumDetail(@Query("m") albumId: String): AlbumResponse

    @GET("track.php")
    suspend fun getAlbumTracks(@Query("m") albumId: String): TrackResponse
}

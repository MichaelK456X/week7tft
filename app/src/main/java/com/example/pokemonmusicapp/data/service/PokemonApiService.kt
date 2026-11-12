package com.example.pokemonmusicapp.data.service

import com.example.pokemonmusicapp.data.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {
    @GET("cards")
    suspend fun searchCards(
        @Query("q") query: String
    ): PokemonResponse
}

package com.example.pokemonmusicapp.data.repository

import com.example.pokemonmusicapp.data.model.PokemonCard
import com.example.pokemonmusicapp.data.service.PokemonApiService

class PokemonRepository(private val apiService: PokemonApiService) {
    suspend fun searchCards(query: String): List<PokemonCard> {
        return apiService.searchCards(nameQuery(query)).data ?: emptyList()
    }

    private fun nameQuery(raw: String): String = "name:\"*$raw*\""
}

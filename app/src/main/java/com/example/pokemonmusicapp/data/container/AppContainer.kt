package com.example.pokemonmusicapp.data.container

import com.example.pokemonmusicapp.data.service.AudioApiService
import com.example.pokemonmusicapp.data.service.PokemonApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
    private val pokemonRetrofit = Retrofit.Builder()
        .baseUrl("https://pokemonpricetracker.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val audioRetrofit = Retrofit.Builder()
        .baseUrl("https://theaudiodb.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pokemonApiService: PokemonApiService by lazy {
        pokemonRetrofit.create(PokemonApiService::class.java)
    }

    val audioApiService: AudioApiService by lazy {
        audioRetrofit.create(AudioApiService::class.java)
    }
}

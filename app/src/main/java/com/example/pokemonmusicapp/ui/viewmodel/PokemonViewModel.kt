package com.example.pokemonmusicapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonmusicapp.data.model.PokemonCard
import com.example.pokemonmusicapp.data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface PokemonUiState {
    object Idle : PokemonUiState
    object Loading : PokemonUiState
    data class Success(val cards: List<PokemonCard>) : PokemonUiState
    data class Error(val message: String) : PokemonUiState
}

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<PokemonUiState>(PokemonUiState.Idle)
    val uiState: StateFlow<PokemonUiState> = _uiState

    fun searchPokemon(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _uiState.value = PokemonUiState.Loading
            try {
                val data = repository.searchCards(query)
                _uiState.value = PokemonUiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = PokemonUiState.Error(e.localizedMessage ?: "Terjadi kesalahan koneksi.")
            }
        }
    }
}

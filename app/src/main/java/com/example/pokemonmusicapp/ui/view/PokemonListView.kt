package com.example.pokemonmusicapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemonmusicapp.ui.theme.*
import com.example.pokemonmusicapp.ui.viewmodel.PokemonUiState
import com.example.pokemonmusicapp.ui.viewmodel.PokemonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListView(viewModel: PokemonViewModel) {
    var query by remember { mutableStateOf("") }
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Text("PokéTracker", color = TextWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Real-time TCG Card Valuation", color = TextGray, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search Card (e.g. Charizard)", color = TextGray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedContainerColor = CardBackground,
                unfocusedContainerColor = CardBackground,
                focusedBorderColor = PrimaryGreen,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Button(
            onClick = { viewModel.searchPokemon(query) },
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("Search Cards", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is PokemonUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryGreen)
            }
            is PokemonUiState.Error -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("⚠️", fontSize = 40.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Connection Failed", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text((state as PokemonUiState.Error).message, color = TextGray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.searchPokemon(query) }, colors = ButtonDefaults.buttonColors(PrimaryGreen)) {
                    Text("Try Again", color = Color.Black)
                }
            }
            is PokemonUiState.Success -> {
                val list = (state as PokemonUiState.Success).cards
                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
                    items(list) { card ->
                        PokemonCard(card = card, onClick = {})
                    }
                }
            }
            else -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Type card name above to search", color = TextGray)
            }
        }
    }
}

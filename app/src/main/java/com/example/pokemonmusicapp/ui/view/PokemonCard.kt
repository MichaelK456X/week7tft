package com.example.pokemonmusicapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokemonmusicapp.data.model.PokemonCard
import com.example.pokemonmusicapp.ui.theme.*

@Composable
fun PokemonCard(card: PokemonCard, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = card.images?.small,
                    contentDescription = card.name,
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                )
                Text(
                    text = "${card.hp ?: 0} HP",
                    color = TextWhite,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(Color.Red, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                        .align(Alignment.TopEnd)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = card.name, color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Bold, maxLines = 1)
            Text(text = card.rarity ?: "Unknown Rarity", color = TextGray, fontSize = 11.sp)

            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "$${card.marketPrice ?: 0.0}", color = PriceGreen, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(text = card.type ?: "None", color = PriceOrange, fontSize = 11.sp)
            }
        }
    }
}

package com.example.pokemonmusicapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokemonmusicapp.ui.theme.*
import com.example.pokemonmusicapp.ui.viewmodel.MusicArtistState
import com.example.pokemonmusicapp.ui.viewmodel.MusicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicArtistView(viewModel: MusicViewModel, onAlbumClick: (String) -> Unit) {
    var query by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    val state by viewModel.artistState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(24.dp).background(PrimaryGreen, RoundedCornerShape(12.dp)))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Music Artist", color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search Artist...", color = TextGray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = TextGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                containerColor = CardBackground,
                focusedBorderColor = PrimaryGreen,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Button(onClick = { viewModel.searchMusicArtist(query) }, colors = ButtonDefaults.buttonColors(PrimaryGreen), modifier = Modifier.fillMaxWidth().padding(top = 6.dp)) {
            Text("Search", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is MusicArtistState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryGreen)
            }
            is MusicArtistState.Error -> Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterVertically) {
                Text("⚠️", fontSize = 40.sp)
                Text("Terjadi Kendala", color = TextWhite, fontWeight = FontWeight.Bold)
                Text((state as MusicArtistState.Error).message, color = TextGray, fontSize = 12.sp)
            }
            is MusicArtistState.Success -> {
                val data = state as MusicArtistState.Success

                data.artist?.let { artist ->
                    AsyncImage(
                        model = artist.strArtistBanner ?: artist.strArtistThumb,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().height(130.dp).background(CardBackground, RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(artist.strArtist, color = TextWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(artist.strGenre ?: "", color = PrimaryGreen, fontSize = 12.sp)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Tentang Artis", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(
                        text = artist.strBiographyEN ?: "",
                        color = TextGray,
                        fontSize = 12.sp,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (isExpanded) "Tutup ringkasan" else "Baca selengkapnya",
                        color = PrimaryGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { isExpanded = !isExpanded }.padding(vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Diskografi Album", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("${data.albums.size} Album", color = PrimaryGreen, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)) {
                    items(data.albums) { album ->
                        Card(
                            modifier = Modifier.padding(4.dp).clickable { onAlbumClick(album.idAlbum) },
                            colors = CardDefaults.cardColors(containerColor = CardBackground)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                AsyncImage(
                                    model = album.strAlbumThumb,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth().height(120.dp).background(DarkBackground, RoundedCornerShape(4.dp))
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(album.strAlbum, color = TextWhite, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 1)
                                Text(album.intYearReleased ?: "-", color = TextGray, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
            else -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Cari nama artis musik di atas.", color = TextGray)
            }
        }
    }
}

package com.example.pokemonmusicapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokemonmusicapp.ui.theme.*
import com.example.pokemonmusicapp.ui.viewmodel.AlbumDetailState
import com.example.pokemonmusicapp.ui.viewmodel.MusicViewModel

@Composable
fun MusicDetailAlbumView(albumId: String, viewModel: MusicViewModel, onBack: () -> Unit) {
    LaunchedEffect(albumId) { viewModel.loadAlbumDetail(albumId) }
    val state by viewModel.albumState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(DarkBackground).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TextWhite)
            }
            Text("Detail Album", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is AlbumDetailState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PrimaryGreen)
            }
            is AlbumDetailState.Error -> Text("Gagal memuat detail album", color = ErrorRed)
            is AlbumDetailState.Success -> {
                val data = state as AlbumDetailState.Success

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        data.album?.let { album ->
                            AsyncImage(
                                model = album.strAlbumThumb,
                                contentDescription = null,
                                modifier = Modifier.size(180.dp).background(CardBackground, RoundedCornerShape(12.dp)).align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(album.strAlbum, color = TextWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Row {
                                Text("Tahun ${album.intYearReleased ?: "-"}  •  ", color = TextGray, fontSize = 12.sp)
                                Text(album.strGenre ?: "", color = PrimaryGreen, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Deskripsi Album", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(album.strDescriptionEN ?: "Tidak ada deskripsi.", color = TextGray, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    item {
                        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("🎵 Daftar Lagu", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("${data.tracks.size} Lagu", color = PrimaryGreen, fontSize = 12.sp)
                        }
                    }

                    itemsIndexed(data.tracks) { index, track ->
                        Row(
                            modifier = Modifier.fillMaxWidth().background(CardBackground, RoundedCornerShape(8.dp)).padding(12.dp).padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = String.format("%02d", index + 1), color = PrimaryGreen, fontWeight = FontWeight.Bold, modifier = Modifier.width(32.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(track.strTrack, color = TextWhite, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                                Text("Pop-Rock", color = TextGray, fontSize = 11.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }
            }
        }
    }
}

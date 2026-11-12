package com.example.pokemonmusicapp.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemonmusicapp.ui.view.MusicArtistView
import com.example.pokemonmusicapp.ui.view.MusicDetailAlbumView
import com.example.pokemonmusicapp.ui.viewmodel.MusicViewModel

@Composable
fun AppRouting(viewModel: MusicViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "artist_search") {
        composable("artist_search") {
            MusicArtistView(viewModel = viewModel, onAlbumClick = { id ->
                navController.navigate("album_detail/$id")
            })
        }
        composable(
            route = "album_detail/{albumId}",
            arguments = listOf(navArgument("albumId") { type = NavType.StringType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId") ?: ""
            MusicDetailAlbumView(albumId = albumId, viewModel = viewModel, onBack = {
                navController.popBackStack()
            })
        }
    }
}

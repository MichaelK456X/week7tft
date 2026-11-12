package com.example.pokemonmusicapp.data.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("data") val data: List<PokemonCard>?
)

data class PokemonCard(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("hp") val hp: String?,
    @SerializedName("images") val images: CardImages?,
    @SerializedName("marketPrice") val marketPrice: Double?,
    @SerializedName("lowestPrice") val lowestPrice: Double?,
    @SerializedName("rarity") val rarity: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("illustrator") val illustrator: String?,
    @SerializedName("activeListings") val activeListings: Int?,
    @SerializedName("verifiedSellers") val verifiedSellers: Int?,
    @SerializedName("primaryPrinting") val primaryPrinting: String?
)

data class CardImages(
    @SerializedName("small") val small: String?,
    @SerializedName("large") val large: String?
)

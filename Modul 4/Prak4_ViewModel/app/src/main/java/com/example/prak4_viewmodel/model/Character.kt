package com.example.prak4_viewmodel.model

import java.io.Serializable

data class Character(
    val name: String,
    val element: String,
    val weaponType: String,
    val rarity: Int,
    val description: String,
    val descriptionDetail: String,
    val youtubeUrl: String,
    val imageRes: Int
) : Serializable

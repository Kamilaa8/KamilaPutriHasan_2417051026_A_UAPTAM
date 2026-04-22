package com.example.utptam.model
import androidx.annotation.DrawableRes

data class foto(
    val judul: String,
    val artist: String,
    @DrawableRes val imageRes: Int
)

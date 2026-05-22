package com.effectivemobile.testtask.domain.model

data class Course (
    val id: String,
    val title: String,
    val description: String,
    val rating: String,
    val imageUrl: String,
    val isFavorite: Boolean = false
)

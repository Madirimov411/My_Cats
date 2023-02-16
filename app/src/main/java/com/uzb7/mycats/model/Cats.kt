package com.uzb7.mycats.model

data class Cats(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)
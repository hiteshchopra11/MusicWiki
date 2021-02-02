package com.news.musicwiki.Model.ArtistsDetails

data class Artist(
    val bio: Bio,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val stats: Stats,
    val url: String
)
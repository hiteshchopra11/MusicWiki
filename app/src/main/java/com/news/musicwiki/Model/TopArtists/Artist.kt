package com.news.musicwiki.Model.TopArtists

data class Artist(
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val url: String
)
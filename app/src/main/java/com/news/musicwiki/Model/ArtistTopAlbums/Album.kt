package com.news.musicwiki.Model.ArtistTopAlbums

data class Album(
    val artist: Artist,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val playcount: Int,
    val url: String
)
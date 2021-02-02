package com.news.musicwiki.Model.ArtistTopTracks

data class Track(
    val artist: Artist,
    val image: List<Image>,
    val listeners: String,
    val mbid: String,
    val name: String,
    val playcount: String,
    val streamable: String,
    val url: String
)
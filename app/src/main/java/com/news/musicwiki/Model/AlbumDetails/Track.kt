package com.news.musicwiki.Model.AlbumDetails

data class Track(
    val artist: Artist,
    val duration: String,
    val name: String,
    val url: String
)
package com.news.musicwiki.Model.TopTracks

data class Track(
    val artist: Artist,
    val duration: String,
    val image: List<Image>,
    val mbid: String,
    val name: String,
    val url: String
)
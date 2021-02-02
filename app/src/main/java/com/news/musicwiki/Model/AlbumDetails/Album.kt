package com.news.musicwiki.Model.AlbumDetails

data class Album(
    val artist: String,
    val image: List<Image>,
    val listeners: String,
    val name: String,
    val playcount: String,
    val tags: Tags,
    val tracks: Tracks,
    val url: String,
    val wiki: Wiki
)
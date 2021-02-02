package com.news.musicwiki.Model.GenreDetails

import com.google.gson.annotations.SerializedName


data class GenreDetailsTag(
    val name: String,
    val reach: Int,
    val total: Int,
    val wiki: Wiki
)
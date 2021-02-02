package com.news.musicwiki.Model.GenreDetails

import com.google.gson.annotations.SerializedName

data class GenreDetails(
    @SerializedName("tag") val genreDetailsTag: GenreDetailsTag
)
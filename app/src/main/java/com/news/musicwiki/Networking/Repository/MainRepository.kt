package com.news.musicwiki.Networking.Repository

import com.news.musicwiki.Networking.ApiHelper

/*Repository Class which acts as mediator between ViewModel and Model(Data)
  It contains suspend function which are only allowed to be called from a
  coroutine or another suspend function */
class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getGenre() = apiHelper.getGenre()
    suspend fun getGenreDetails(tag: String) = apiHelper.getGenreDetails(tag)
    suspend fun getTopAlbums(tag: String) = apiHelper.getTopAlbums(tag)
    suspend fun getTopArtists(tag: String) = apiHelper.getTopArtists(tag)
    suspend fun getTopTracks(tag: String) = apiHelper.getTopTracks(tag)
    suspend fun getAlbumDetails(artist: String, album: String) =
        apiHelper.getAlbumDetails(artist, album)

    suspend fun getTopGenres(artist: String, album: String) = apiHelper.getTopGenres(artist, album)
    suspend fun getArtistDetails(artist: String) =
        apiHelper.getArtistDetails(artist)

    suspend fun getArtistTags(mbid: String) =
        apiHelper.getArtistTags(mbid)

    suspend fun getArtistAlbums(mbid: String) =
        apiHelper.getArtistAlbums(mbid)

    suspend fun getArtistTracks(mbid: String) =
        apiHelper.getArtistTracks(mbid)

    suspend fun getArtistInfo(mbid: String) =
        apiHelper.getArtistInfo(mbid)

}
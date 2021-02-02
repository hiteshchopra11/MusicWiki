package com.news.musicwiki.Networking

/*APIHelper class which contains all API related functions
and we pass out API key,location (which are static in nature) here itself*/
class ApiHelper(private val apiService: ApiService) {
    private var apiKey: String = "6538181219d6352c4ed4b34e1235df80"
    private var format: String = "json"
    suspend fun getGenre() = apiService.getGenre(apiKey = apiKey, format = format)
    suspend fun getGenreDetails(tag: String) =
        apiService.getGenreDetails(apiKey = apiKey, format = format, tag = tag)

    suspend fun getTopAlbums(tag: String) =
        apiService.getTopAlbums(apiKey = apiKey, format = format, tag = tag)

    suspend fun getTopArtists(tag: String) =
        apiService.getTopArtists(apiKey = apiKey, format = format, tag = tag)

    suspend fun getTopTracks(tag: String) =
        apiService.getTopTracks(apiKey = apiKey, format = format, tag = tag)

    suspend fun getAlbumDetails(artist: String, album: String) =
        apiService.getAlbumDetails(apiKey = apiKey, format = format, artist = artist, album = album)

    suspend fun getTopGenres(artist: String, album: String) =
        apiService.getTopGenres(apiKey = apiKey, format = format, artist = artist, album = album)

    suspend fun getArtistDetails(artist: String) =
        apiService.getArtistDetails(apiKey = apiKey, format = format, artist = artist)

    suspend fun getArtistTags(mbid: String) =
        apiService.getArtistTags(apiKey = apiKey, format = format, mbid = mbid)

    suspend fun getArtistAlbums(mbid: String) =
        apiService.getArtistAlbums(apiKey = apiKey, format = format, mbid = mbid)

    suspend fun getArtistTracks(mbid: String) =
        apiService.getArtistTracks(apiKey = apiKey, format = format, mbid = mbid)

    suspend fun getArtistInfo(mbid: String) =
        apiService.getArtistInfo(apiKey = apiKey, format = format, mbid = mbid)

}
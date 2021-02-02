package com.news.musicwiki.Networking


import com.news.musicwiki.Model.AlbumDetails.AlbumDetailsModel
import com.news.musicwiki.Model.ArtistTopAlbums.ArtistTopAlbums
import com.news.musicwiki.Model.ArtistTopTracks.ArtistTopTracks
import com.news.musicwiki.Model.ArtistsDetails.ArtistsDetails
import com.news.musicwiki.Model.Genre.Genre
import com.news.musicwiki.Model.GenreDetails.GenreDetails
import com.news.musicwiki.Model.TopAlbums.TopAlbums
import com.news.musicwiki.Model.TopArtists.TopArtists
import com.news.musicwiki.Model.TopGenres.TopGenres
import com.news.musicwiki.Model.TopTracks.TopTracks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/*APIService class is the interface which defines our API endpoints(Retrofit), their queries as
  well as their response types all at one place*/
interface ApiService {
    @GET("?method=tag.getTopTags")
    suspend fun getGenre(
        @Query(value = "format") format: String,
        @Query(value = "api_key") apiKey: String
    ): Response<Genre>

    @GET("?method=tag.getinfo")
    suspend fun getGenreDetails(
        @Query(value = "tag") tag: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<GenreDetails>

    @GET("?method=tag.gettopalbums")
    suspend fun getTopAlbums(
        @Query(value = "tag") tag: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<TopAlbums>

    @GET("?method=tag.gettopartists")
    suspend fun getTopArtists(
        @Query(value = "tag") tag: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<TopArtists>

    @GET("?method=tag.gettoptracks")
    suspend fun getTopTracks(
        @Query(value = "tag") tag: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<TopTracks>

    @GET("?method=album.getinfo")
    suspend fun getAlbumDetails(
        @Query(value = "artist", encoded = true) artist: String,
        @Query(value = "album", encoded = true) album: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<AlbumDetailsModel>

    @GET("?method=album.gettoptags")
    suspend fun getTopGenres(
        @Query(value = "artist", encoded = true) artist: String,
        @Query(value = "album", encoded = true) album: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<TopGenres>

    @GET("?method=artist.getinfo")
    suspend fun getArtistDetails(
        @Query(value = "artist", encoded = true) artist: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<ArtistsDetails>

    @GET("?method=artist.getinfo")
    suspend fun getArtistInfo(
        @Query(value = "mbid") mbid: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<ArtistsDetails>

    @GET("?method=artist.getTopTags")
    suspend fun getArtistTags(
        @Query(value = "mbid") mbid: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<TopGenres>

    @GET("?method=artist.getTopAlbums")
    suspend fun getArtistAlbums(
        @Query(value = "mbid") mbid: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<ArtistTopAlbums>

    @GET("?method=artist.getTopTracks")
    suspend fun getArtistTracks(
        @Query(value = "mbid") mbid: String,
        @Query(value = "api_key") apiKey: String,
        @Query(value = "format") format: String
    ): Response<ArtistTopTracks>

}
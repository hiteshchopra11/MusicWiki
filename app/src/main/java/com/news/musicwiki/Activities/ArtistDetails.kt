package com.news.musicwiki.Activities

import RetrofitBuilder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.musicwiki.Utils.Adapters.GenreAdapter
import com.news.musicwiki.Model.ArtistTopAlbums.Album

import com.news.musicwiki.Model.ArtistTopAlbums.ArtistTopAlbums
import com.news.musicwiki.Model.ArtistTopTracks.ArtistTopTracks
import com.news.musicwiki.Model.ArtistTopTracks.Track
import com.news.musicwiki.Model.ArtistsDetails.ArtistsDetails
import com.news.musicwiki.Model.Genre.Tag
import com.news.musicwiki.Model.TopGenres.TopGenres
import com.news.musicwiki.Networking.ApiHelper
import com.news.musicwiki.R
import com.news.musicwiki.Utils.Adapters.ArtistTopAlbumAdapter
import com.news.musicwiki.Utils.Adapters.ArtistTopTracksAdapter

import com.news.musicwiki.Utils.Status
import com.news.musicwiki.ViewModels.ViewModelFactory
import com.news.musicwiki.ViewModels.ViewModels
import kotlinx.android.synthetic.main.activity_artist_details.*
import retrofit2.Response
import kotlin.math.ln

class ArtistDetails : AppCompatActivity() {
    //Late initialize views
    private lateinit var viewModel: ViewModels
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var topAlbumsAdapter: ArtistTopAlbumAdapter
    private lateinit var topTracksAdapter: ArtistTopTracksAdapter
    private lateinit var genreRecyclerView: RecyclerView
    private lateinit var albumsRecyclerView: RecyclerView
    private lateinit var tracksRecyclerView: RecyclerView

    lateinit var mbid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist_details)
        val bundle = intent.extras
        mbid = bundle!!.getString("mbid").toString()
        initViews()
        setupUI()
        setupViewModel()
        setupObservers()
    }

    //Attach adapters to recyclerview
    private fun setupUI() {
        genreRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        genreAdapter = GenreAdapter(arrayListOf(), -1)
        genreRecyclerView.adapter = genreAdapter

        albumsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        topAlbumsAdapter = ArtistTopAlbumAdapter(arrayListOf())
        albumsRecyclerView.adapter = topAlbumsAdapter

        tracksRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        topTracksAdapter = ArtistTopTracksAdapter(arrayListOf())
        tracksRecyclerView.adapter = topTracksAdapter
    }

    //Setup ViewModels
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(ViewModels::class.java)
    }

    //Setup Observers
    private fun setupObservers() {
        viewModel.getArtistTags(mbid = mbid)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {

                            resource.data?.let { genres -> retrieveGenreList(genres) }
                        }
                        Status.ERROR -> {
                            Log.e("Response", it.message.toString())
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            })

        viewModel.getArtistAlbums(mbid = mbid)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Log.e("Response", "yes" + resource.data!!.code().toString())
                            retrieveAlbumsList(resource.data)
                        }
                        Status.ERROR -> {
                            Log.e("Error", it.message.toString())
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })

        viewModel.getArtistTracks(mbid = mbid)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Log.e("ResponseCheck", resource.data!!.body().toString())
                            retrieveTracksList(resource.data)
                        }
                        Status.ERROR -> {
                            Log.e("Error", it.message.toString())
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            })

        viewModel.getArtistInfo(mbid = mbid)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Log.e("Response", resource.data.toString())
                            resource.data?.let { artistInfo -> setArtistDetails(artistInfo) }
                        }
                        Status.ERROR -> {
                            Log.e("Error", it.message.toString())
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            })
    }

    //Retrieve data and add in our adapters
    private fun retrieveGenreList(genres: Response<TopGenres>) {
        val tags: List<Tag> = genres.body()!!.toptags.tag
        genreAdapter.apply {
            addGenre(tags)
            notifyDataSetChanged()
        }
    }

    //Retrieve data and add in our adapters
    private fun retrieveAlbumsList(genres: Response<ArtistTopAlbums>) {
        val albums: List<Album> = genres.body()!!.topalbums.album
        topAlbumsAdapter.apply {
            addTopAlbums(albums)
            notifyDataSetChanged()
        }
    }

    //Retrieve data and add in our adapters
    private fun retrieveTracksList(genres: Response<ArtistTopTracks>) {
        val tracks: List<Track> = genres.body()!!.toptracks.track
        topTracksAdapter.apply {
            addTopTracks(tracks)
            notifyDataSetChanged()
        }
    }

    //Retrieve details and adds to our TextView
    private fun setArtistDetails(artistDetail: Response<ArtistsDetails>) {
        val playCount: Long = artistDetail.body()!!.artist.stats.playcount.toLong()
        val playCountString = numbersCount(playCount)
        val followersCount: Long = artistDetail.body()!!.artist.stats.listeners.toLong()
        val followersCountString = numbersCount(followersCount)
        val imageUrl = artistDetail.body()!!.artist.image[2].url
        artist_description.text = artistDetail.body()!!.artist.bio.summary
        play_count_number.text = playCountString
        followers_number.text = followersCountString
        Glide.with(album_image.context)
            .load(imageUrl)
            .placeholder(R.drawable.loading)
            .into(album_image)
    }

    //Initialize recyclerView
    private fun initViews() {
        genreRecyclerView = findViewById(R.id.genreRecycleView)
        tracksRecyclerView = findViewById(R.id.tracksRecycleView)
        albumsRecyclerView = findViewById(R.id.albumsRecycleView)
    }

    fun numbersCount(count: Long): String {
        if (count < 1000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format(
            "%.1f %c",
            count / Math.pow(1000.0, exp.toDouble()),
            "kMGTPE"[exp - 1]
        )
    }
}
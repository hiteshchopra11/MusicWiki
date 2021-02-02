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
import com.news.musicwiki.Model.AlbumDetails.AlbumDetailsModel
import com.news.musicwiki.Model.ArtistsDetails.ArtistsDetails
import com.news.musicwiki.Model.Genre.Tag
import com.news.musicwiki.Model.TopGenres.TopGenres
import com.news.musicwiki.Networking.ApiHelper
import com.news.musicwiki.R
import com.news.musicwiki.Utils.Status
import com.news.musicwiki.ViewModels.ViewModelFactory
import com.news.musicwiki.ViewModels.ViewModels
import kotlinx.android.synthetic.main.activity_album_details.*
import retrofit2.Response
import java.net.URLEncoder

class AlbumDetails : AppCompatActivity() {
    //Late initialize views
    private lateinit var viewModel: ViewModels
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var album: String
    lateinit var artist: String
    lateinit var albumEncoded: String
    lateinit var artistEncoded: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)
        val bundle = intent.extras
        album = bundle!!.getString("albumName").toString()
        artist = bundle.getString("artistName").toString()
        albumEncoded = URLEncoder.encode(album, "UTF-8").replace("+", "%20")
        artistEncoded = URLEncoder.encode(artist, "UTF-8").replace("+", "%20")
        initViews()
        setupUI()
        setupViewModel()
        setupObservers()
    }

    //Attach adapters to recyclerview
    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        // Display all the items by passing -1 as argument(Logic in Adapter Class)
        genreAdapter = GenreAdapter(arrayListOf(), -1)
        recyclerView.adapter = genreAdapter
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
        viewModel.getTopGenres(artist = artistEncoded, album = albumEncoded)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { genres -> retrieveList(genres) }
                        }
                        Status.ERROR -> {
                            Log.e("Response", it.message.toString())
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })

        viewModel.getAlbumDetails(artist = artistEncoded, album = albumEncoded)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Log.e("Response", resource.data.toString())
                            resource.data?.let { albumDetails -> setAlbumDetails(albumDetails) }
                        }
                        Status.ERROR -> {
                            Log.e("Error", it.message.toString())
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })

        viewModel.getArtistDetails(artist = artistEncoded)
            .observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            Log.e("Response", resource.data.toString())
                            resource.data?.let { artistDetails -> setArtistDetails(artistDetails) }
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
    private fun retrieveList(genres: Response<TopGenres>) {
        val tags: List<Tag> = genres.body()!!.toptags.tag
        genreAdapter.apply {
            addGenre(tags)
            notifyDataSetChanged()
        }
    }

    //Retrieve genre details and adds to our TextView
    private fun setAlbumDetails(albumsDetail: Response<AlbumDetailsModel>) {
        val imageUrl: String = albumsDetail.body()!!.album.image[3].url
        album_name.text = albumsDetail.body()!!.album.name
        artist_name.text = albumsDetail.body()!!.album.artist.toString()
        Glide.with(album_image.context)
            .load(imageUrl)
            .placeholder(R.drawable.loading)
            .into(album_image)
    }

    //Retrieve genre details and adds to our TextView
    private fun setArtistDetails(artistDetail: Response<ArtistsDetails>) {
        val artistInformation: String = artistDetail.body()!!.artist.bio.summary
        text_artist_details.text=artistInformation
    }

    //Initialize recyclerView
    private fun initViews() {
        recyclerView = findViewById(R.id.recycleView)
    }
}
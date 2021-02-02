package com.news.musicwiki.Fragments

import RetrofitBuilder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.musicwiki.Model.TopArtists.Artist
import com.news.musicwiki.Model.TopArtists.TopArtists
import com.news.musicwiki.Networking.ApiHelper
import com.news.musicwiki.R
import com.news.musicwiki.Utils.Adapters.TopArtistsAdapter
import com.news.musicwiki.Utils.Status
import com.news.musicwiki.ViewModels.ViewModelFactory
import com.news.musicwiki.ViewModels.ViewModels
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

open class ArtistsFragment : Fragment() {
    //Late initialize views
    private lateinit var viewModel: ViewModels
    private lateinit var topArtistsAdapter: TopArtistsAdapter
    private lateinit var recyclerView: RecyclerView
    lateinit var genre: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        genre = this.arguments?.getString("tag").toString()
        return inflater.inflate(R.layout.fragment_artists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        genre = this.arguments?.getString("tag").toString()
        //Initialize lately initialized views,setup ViewModels,attach adapters to recyclerview
        //and setup Observers(for API calls) using user defined functions
        //Make network error TextView invisible
        if (::topArtistsAdapter.isInitialized) {
            if (topArtistsAdapter.itemCount > 0)
                recyclerView.adapter = null
        }
        initViews()
        setupUI()
        setupViewModel()
        setupObservers()
    }

    //Attach adapters to recyclerview
    private fun setupUI() {
        recyclerView.layoutManager = GridLayoutManager(
            activity,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as GridLayoutManager).orientation
            )
        )
        topArtistsAdapter = TopArtistsAdapter(arrayListOf())
        recyclerView.adapter = topArtistsAdapter
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
        activity?.let { it ->
            viewModel.getTopArtists(genre).observe(it, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (resource.data?.code() == 200) {
                                progressBar.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                                retrieveList(resource.data)
                            } else {
                                progressBar.visibility = View.GONE
                                textViewEmpty.text =
                                    "Some error has occurred with status Code " + resource.data!!.code()
                                textViewEmpty.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                        }
                        Status.ERROR -> {
                            progressBar.visibility = View.GONE
                            recyclerView.visibility = View.GONE
                            //Show network error textView
                            textViewEmpty.visibility = View.VISIBLE
                        }
                        Status.LOADING -> {
                            progressBar.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }

    //Retrieve data and add in our adapters
    private fun retrieveList(topAlbums: Response<TopArtists>) {
        val albums: List<Artist> = topAlbums.body()!!.topartists.artist
        topArtistsAdapter.apply {
            addTopArtists(albums)
            notifyDataSetChanged()
        }
    }

    //Initialize recyclerView
    private fun initViews() {
        recyclerView = requireView().findViewById(R.id.recycleView)
    }
}
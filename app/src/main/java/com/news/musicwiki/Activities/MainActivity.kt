package com.news.musicwiki.Activities

import RetrofitBuilder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.musicwiki.Utils.Adapters.GenreAdapter
import com.news.musicwiki.Model.Genre.Genre
import com.news.musicwiki.Model.Genre.Tag
import com.news.musicwiki.Networking.ApiHelper
import com.news.musicwiki.R
import com.news.musicwiki.Utils.Status
import com.news.musicwiki.ViewModels.ViewModelFactory
import com.news.musicwiki.ViewModels.ViewModels
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //Late initialize views
    private lateinit var viewModel: ViewModels
    private lateinit var genreAdapterMore: GenreAdapter
    private lateinit var genreAdapterLess: GenreAdapter
    private lateinit var recyclerView: RecyclerView
    private var flagLess = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setupUI()
        setupViewModel()
        setupObservers()
        textViewEmpty.visibility = View.GONE
        load_more_button.setOnClickListener(this)
    }

    //Attach adapters to recyclerview
    private fun setupUI() {
        recyclerView.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        // Display only 12 items instead of the total size initially (4x3)
        genreAdapterLess = GenreAdapter(arrayListOf(), 12)
        // Display all the items by passing -1 as argument(Logic in Adapter Class)
        genreAdapterMore = GenreAdapter(arrayListOf(), -1)
        recyclerView.adapter = genreAdapterLess
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
        viewModel.getGenre().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        if (resource.data!!.code() == 200) {
                            progressBar.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                            //Retrieve response and add to adapters using this function
                            retrieveList(resource.data)
                        }
                        else{
                            progressBar.visibility = View.GONE
                            textViewEmpty.text="Some error has occurred with status Code "+ resource.data.code()
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

    //Retrieve data and add in our adapters
    private fun retrieveList(genres: Response<Genre>) {
        val tags: List<Tag> = genres.body()!!.toptags.tag
        //To display less items
        genreAdapterLess.apply {
            addGenre(tags)
            notifyDataSetChanged()
        }
        //To display more items items
        genreAdapterMore.apply {
            addGenre(tags)
            notifyDataSetChanged()
        }
    }

    //Initialize recyclerView
    private fun initViews() {
        recyclerView = findViewById(R.id.recycleView)
    }

    override fun onClick(p0: View?) {
        load_more_button.setOnClickListener {
            Log.e("HERE", "Test")
            if (!flagLess) {
                Log.e("HERE", "lessFalse")
                recyclerView.adapter = genreAdapterLess
                load_more_button.setImageResource(R.drawable.ic_round_arrow_drop_down_24)
                flagLess = true
            } else {
                Log.e("HERE", "lessTrue")
                recyclerView.adapter = genreAdapterMore
                load_more_button.setImageResource(R.drawable.ic_round_arrow_drop_up_24)
                flagLess = false
            }
        }
    }
}
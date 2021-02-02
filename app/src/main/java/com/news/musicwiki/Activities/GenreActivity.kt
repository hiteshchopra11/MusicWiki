package com.news.musicwiki.Activities

import com.news.musicwiki.Fragments.AlbumsFragment
import RetrofitBuilder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.news.musicwiki.Fragments.ArtistsFragment
import com.news.musicwiki.Fragments.TracksFragment
import com.news.musicwiki.Model.GenreDetails.GenreDetails
import com.news.musicwiki.Model.GenreDetails.GenreDetailsTag
import com.news.musicwiki.Model.GenreDetails.Wiki
import com.news.musicwiki.Networking.ApiHelper
import com.news.musicwiki.R
import com.news.musicwiki.Utils.Status
import com.news.musicwiki.ViewModels.ViewModelFactory
import com.news.musicwiki.ViewModels.ViewModels
import kotlinx.android.synthetic.main.activity_genre.*
import retrofit2.Response

class GenreActivity : AppCompatActivity() {

    //Lateinit to initialize the variables later(in initViews())
    private lateinit var viewModel: ViewModels
    private lateinit var genreName: String
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    //Title of viewpager
    private val titles = arrayOf("Albums", "Artists", "Tracks")
    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genre)
        val bundle = Bundle()
        bundle.putString("tag", intent.extras?.getString("genreName").toString())
        val albumFrag = AlbumsFragment()
        val artistsFrag = ArtistsFragment()
        val tracksFrag = TracksFragment()

        albumFrag.arguments = bundle
        artistsFrag.arguments = bundle
        tracksFrag.arguments = bundle

        fragmentList.add(albumFrag)
        fragmentList.add(artistsFrag)
        fragmentList.add(tracksFrag)

        //Initialize Views, Attach Adapter to ViewPager and set Toolbar in our MainActivity
        initViews()
        attachAdapter()
        setGenreName()
        setupViewModel()
        setupObservers()
    }

    private fun attachAdapter() {
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }
        }
        val tabLayoutMediator = TabLayoutMediator(
            tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }

        //Attach to TabLayoutMediator(Viewpager2)
        tabLayoutMediator.attach()
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tab_layout)
    }

    //Set Genre Name in TextView
    private fun setGenreName() {
        // Get genre name (value) from previous activity
        val extras = intent.extras
        if (extras != null) {
            genreName = extras.getString("genreName").toString()
        }

        //Capitalize the first character of the genre name
        //Eg change rock to Rock
        ("" + genreName[0].toUpperCase() + genreName.subSequence(
            1,
            genreName.length
        )).also { text_genre_name.text = it }
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
        viewModel.getGenreDetails(genreName).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { detail -> setGenreDescription(detail) }
                    }
                    Status.ERROR -> {
                        text_genre_name.text="Some network error occurred. Please try again later"
                    }
                    Status.LOADING -> {
                    }
                }
            }
        })
    }

    //Retrieve genre details and adds to our TextView
    private fun setGenreDescription(genres: Response<GenreDetails>) {
        val tag: GenreDetailsTag? = genres.body()?.genreDetailsTag
        val wiki: Wiki? = tag?.wiki
        val summary: String? = wiki?.summary
        text_genre_details.text = summary
    }
}
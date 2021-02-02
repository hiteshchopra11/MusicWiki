package com.news.musicwiki.Utils.Adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.musicwiki.Activities.ArtistDetails
import com.news.musicwiki.Model.TopArtists.Artist
import com.news.musicwiki.R
import kotlinx.android.synthetic.main.top_album_row.view.*

class TopArtistsAdapter(private val artists: ArrayList<Artist>) :
    RecyclerView.Adapter<TopArtistsAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Bind data to Views
        fun bind(artist: Artist) {
            itemView.apply {
                album_name.visibility = View.GONE
                artist_name.text = artist.name
                Glide.with(top_album_image.context)
                    .asBitmap()
                    .load(artist.image[2].url)
                    .placeholder(R.drawable.loading)
                    .into(top_album_image)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, ArtistDetails::class.java)
                    Log.e("MBID in ADAPTER",artist.mbid)
                    intent.putExtra("mbid", artist.mbid)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.top_album_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(artists[position])
    }

    //Function to add genre to ArrayList of genre
    fun addTopArtists(topArtists: List<Artist>) {
        this.artists.apply {
            clear()
            addAll(topArtists)
        }
    }
}
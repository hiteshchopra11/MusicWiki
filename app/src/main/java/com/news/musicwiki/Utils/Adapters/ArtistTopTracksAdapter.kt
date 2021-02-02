package com.news.musicwiki.Utils.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.musicwiki.Model.ArtistTopTracks.Track
import com.news.musicwiki.R
import kotlinx.android.synthetic.main.top_album_row.view.*

class ArtistTopTracksAdapter(private val tracks: ArrayList<Track>) :
    RecyclerView.Adapter<ArtistTopTracksAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Bind data to Views
        fun bind(track: Track) {
            itemView.apply {
                album_name.text = track.name
                artist_name.text = track.artist.name
                Glide.with(top_album_image.context)
                    .asBitmap()
                    .load(track.image[2].url)
                    .placeholder(R.drawable.loading)
                    .into(top_album_image)
                itemView.setOnClickListener {

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
        return tracks.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    //Function to add genre to ArrayList of genre
    fun addTopTracks(topTracks: List<Track>) {
        this.tracks.apply {
            clear()
            addAll(topTracks)
        }
    }
}
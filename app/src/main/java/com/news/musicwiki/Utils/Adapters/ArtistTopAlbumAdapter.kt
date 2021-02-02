package com.news.musicwiki.Utils.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.news.musicwiki.Activities.AlbumDetails
import com.news.musicwiki.Model.ArtistTopAlbums.Album
import com.news.musicwiki.R
import kotlinx.android.synthetic.main.top_album_row.view.*

class ArtistTopAlbumAdapter(private val albums: ArrayList<Album>) :
    RecyclerView.Adapter<ArtistTopAlbumAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Bind data to Views
        fun bind(album: Album) {
            itemView.apply {
                album_name.text = album.name
                artist_name.text = album.artist.name
                Glide.with(top_album_image.context)
                    .asBitmap()
                    .load(album.image[2].url)
                    .placeholder(R.drawable.loading)
                    .into(top_album_image)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, AlbumDetails::class.java)
                    intent.putExtra("albumName", album.name)
                    intent.putExtra("artistName", album.artist.name)
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
        return albums.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(albums[position])
    }

    //Function to add genre to ArrayList of genre
    fun addTopAlbums(topAlbums: List<Album>) {
        this.albums.apply {
            clear()
            addAll(topAlbums)
        }
    }
}
package com.news.musicwiki.Utils.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.news.musicwiki.Activities.GenreActivity
import com.news.musicwiki.Model.Genre.Tag
import com.news.musicwiki.R
import kotlinx.android.synthetic.main.genre_row.view.*


class GenreAdapter(private val genres: ArrayList<Tag>, private val count: Int) :
    RecyclerView.Adapter<GenreAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Bind data to Views
        fun bind(tag: Tag) {
            itemView.apply {
                genre_name.text = tag.name
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, GenreActivity::class.java)
                intent.putExtra("genreName", tag.name)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.genre_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (count != -1)
            count
        else {
            genres.size
        }
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    //Function to add genre to ArrayList of genre
    fun addGenre(topTags: List<Tag>) {
        this.genres.apply {
            clear()
            addAll(topTags)
        }
    }
}
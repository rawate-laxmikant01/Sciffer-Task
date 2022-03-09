package com.example.sciffertask.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sciffertask.Activity.MovieDetails
import com.example.sciffertask.Model.MovieModel
import com.example.sciffertask.R


class SavedMovieAdapter(val savedMovieList: ArrayList<MovieModel>, val context: Context) : RecyclerView.Adapter<SavedMovieAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_saved_movie, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.movieName.text = savedMovieList[position].Title
        holder.genre.text = savedMovieList[position].Genre
        holder.like.text = savedMovieList[position].imdbVotes
        holder.releaseDate.text = savedMovieList[position].Released

        Glide.with(context)
            .load(savedMovieList[position].Poster)
            .into(holder.movieImg)


        holder.childViewBtn.setOnClickListener {
            val intent = Intent(context,MovieDetails::class.java)
            intent.putExtra("searchMovie", savedMovieList[position].Title)
            context.startActivity(intent)
        }



    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return savedMovieList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val movieName: TextView = itemView.findViewById(R.id.rv_movieName)
        val releaseDate: TextView = itemView.findViewById(R.id.rv_releaseDate)
        val like: TextView = itemView.findViewById(R.id.like)
        val genre: TextView = itemView.findViewById(R.id.rvgenre)
        val movieImg: ImageView = itemView.findViewById(R.id.rvsavedMovieImg)
        val childViewBtn: LinearLayout = itemView.findViewById(R.id.child_view)
    }
}
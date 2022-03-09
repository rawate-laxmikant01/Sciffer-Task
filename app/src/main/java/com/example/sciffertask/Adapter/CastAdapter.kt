package com.example.sciffertask.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sciffertask.R


class CastAdapter(val castList: Array<String>, val context: Context) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_cast_view, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val actorName = castList.get(position)

        // sets the image to the imageview from our itemHolder class
//        holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.actorName.text = actorName
        Glide.with(context)
            .load(R.drawable.actor_dummy_img)
            .circleCrop()
            .into(holder.actorImg)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return castList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val actorName: TextView = itemView.findViewById(R.id.castName)
        val actorImg: ImageView = itemView.findViewById(R.id.castImg)
    }
}
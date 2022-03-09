package com.example.sciffertask.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quadbtechtask.utility.RetrofitHelper
import com.example.sciffertask.Adapter.CastAdapter
import com.example.sciffertask.Interface.MoviesApi
import com.example.sciffertask.R
import com.example.sciffertask.databinding.ActivityMovieDetailsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MovieDetails : AppCompatActivity() {

    val API_KEY = "38682202"
    var firebaseDatabase: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    lateinit var progress_circular_detail: ProgressBar



    private lateinit var binding: ActivityMovieDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val castRecycler: RecyclerView = binding.castRecyclerView
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase!!.getReference("Movie")
        progress_circular_detail = findViewById(R.id.progress_circular_detail)
        val str: String = intent.getStringExtra("searchMovie").toString()

        val moviesApi = RetrofitHelper.getInstance().create(MoviesApi::class.java)

        // launching a new coroutine
        GlobalScope.launch {
            val result = moviesApi.getMovies(str, API_KEY)
            val moviedata = result.body()

            Log.d("movie: ", result.body().toString())

            runOnUiThread {
                //stuff that updates ui

                Glide.with(this@MovieDetails)
                    .load(moviedata?.Poster)
                    .into(binding.movieBannerImage)

                binding.movieName.text = moviedata?.Title
                binding.like.text = moviedata!!.imdbVotes
                binding.releaseData.text = moviedata.Released
                binding.language.text = moviedata.Language
                binding.genre.text = moviedata.Genre
                binding.aboutMovie.text = moviedata.Plot

                progress_circular_detail.visibility = View.GONE

                binding.intrestedBtn.setOnClickListener {

                    databaseReference!!.push().setValue(moviedata).addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this@MovieDetails, "Movie Saved", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MovieDetails,ContainerActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }


                val castList: Array<String> = moviedata.Actors?.split(",")?.toTypedArray()!!

                Log.d("actor: ", castList[0])


                castRecycler.layoutManager =
                    LinearLayoutManager(this@MovieDetails, LinearLayoutManager.HORIZONTAL, false)
                castRecycler.adapter = CastAdapter(castList, this@MovieDetails)

            }

        }

    }

}
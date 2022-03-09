package com.example.sciffertask.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.sciffertask.R
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sciffertask.Adapter.SavedMovieAdapter
import com.example.sciffertask.Model.MovieModel
import com.example.sciffertask.utility.GridSpacingItemDecoration
import com.google.firebase.database.*


class ContainerActivity : AppCompatActivity() {

    var savedMovieList=ArrayList<MovieModel>()
    lateinit var firebaseDatabase :FirebaseDatabase
    lateinit var databaseReference :DatabaseReference
    lateinit var savedMovieRecycler: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var searchString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        firebaseDatabase= FirebaseDatabase.getInstance()
        databaseReference =  firebaseDatabase.getReference("Movie")
        savedMovieRecycler= findViewById(R.id.savedMovieRecycler)
        val btnSearch: Button = findViewById(R.id.btnSearch)
        progressBar = findViewById(R.id.progress_circular)

        findViewById<EditText>(R.id.searchMovie).addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                searchString = s.toString()
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int,
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int,
            ) {
            }
        })

//        savedMovieRecycler.layoutManager = GridLayoutManager(this, 2)
        val numberOfColumns = 2
        val spanCount = 2
        val spacing = 50
        val includeEdge = true

        savedMovieRecycler.layoutManager =
            GridLayoutManager(this, numberOfColumns)

        savedMovieRecycler.addItemDecoration(
            GridSpacingItemDecoration(             //This class is created for giving equal spacing to grid.
                spanCount,
                spacing,
                includeEdge
            )
        )

        fetchData()


        btnSearch.setOnClickListener {

            val intent = Intent(this, MovieDetails::class.java)
            intent.putExtra("searchMovie", searchString)
            startActivity(intent)

        }

    }


    fun fetchData(){
        //Data fetching from saved database
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(usersnapshot in snapshot.children){
                        val tasks=usersnapshot.getValue(MovieModel::class.java)
                        savedMovieList.add(tasks!!)
                        progressBar.visibility = View.GONE
                    }

                     val   savedMovieAdapter= SavedMovieAdapter(savedMovieList, this@ContainerActivity)
                    savedMovieRecycler.adapter = savedMovieAdapter

                    savedMovieAdapter.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ContainerActivity, "unable to get the data", Toast.LENGTH_SHORT).show()
            }

        })
    }

}



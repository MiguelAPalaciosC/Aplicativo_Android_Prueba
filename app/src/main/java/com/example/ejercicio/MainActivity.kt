package com.example.ejercicio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.adapter.PeopleAdapter
import com.example.ejercicio.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

private const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    //lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //PeopleProvider.peopleList
        setContentView(R.layout.activity_main)
        initRecyclerView()
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setUpFab()
        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = Firebase.analytics
    }

    private fun setUpFab() {
        //val fab = binding.fab
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerTest)
        fab.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra(EXTRA_MESSAGE, "Error")
            }
            startActivity(intent)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    fab.collapse()
                } else {
                    fab.expand()
                }
            }
        })
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerTest)
        recyclerView.layoutManager = manager
        recyclerView.adapter = PeopleAdapter(PeopleProvider.peopleList)
        recyclerView.addItemDecoration(decoration)
    }
}
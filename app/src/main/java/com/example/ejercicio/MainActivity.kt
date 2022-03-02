package com.example.ejercicio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.adapter.PeopleAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //PeopleProvider.peopleList
        setContentView(R.layout.activity_main)
        initRecyclerView()
        fab.setOnClickListener{
            Toast.makeText(this,"Holalaaaaaa",Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecyclerView() {
        val manager=LinearLayoutManager(this)
        val decoration=DividerItemDecoration(this,manager.orientation)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerTest)
        recyclerView.layoutManager = manager
        recyclerView.adapter = PeopleAdapter(PeopleProvider.peopleList)
        recyclerView.addItemDecoration(decoration)
    }
}
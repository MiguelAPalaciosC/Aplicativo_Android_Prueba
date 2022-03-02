package com.example.ejercicio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.People
import com.example.ejercicio.R

class PeopleAdapter(private val peopleList: List<People>) : RecyclerView.Adapter<PeopleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PeopleViewHolder(layoutInflater.inflate(R.layout.item_people, parent, false))
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val item=peopleList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = peopleList.size

}
package com.example.ejercicio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.People
import com.example.ejercicio.R

class PeopleAdapter(private val peopleList: List<People>, val context:Context) : RecyclerView.Adapter<PeopleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        return PeopleViewHolder(layoutInflater.inflate(R.layout.item_people, parent, false))
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val item=peopleList[position]
        holder.render(item,context)
        //holder.people.text =peopleList[position].name
        //holder.age.text =peopleList[position].age.toString()
        //holder.number.text =peopleList[position].number
        //holder.photo =peopleList[position].photo.toString()
    }

    override fun getItemCount(): Int = peopleList.size

}
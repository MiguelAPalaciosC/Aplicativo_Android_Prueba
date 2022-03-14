package com.example.ejercicio.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejercicio.MainActivity
import com.example.ejercicio.People
import com.example.ejercicio.R
import com.google.firebase.firestore.FirebaseFirestore
import com.mikhaellopez.circularimageview.CircularImageView

class PeopleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private lateinit var db: FirebaseFirestore

    var idVH: String = ""

    var photo = view.findViewById<CircularImageView>(R.id.ivPeople)
    val people = view.findViewById<TextView>(R.id.tvPeopleName)
    val age = view.findViewById<TextView>(R.id.tvAge)
    val number = view.findViewById<TextView>(R.id.tvNumber)
    val state = view.findViewById<TextView>(R.id.tvState)
    val switch = view.findViewById<Switch>(R.id.stState)

    fun render(peopleModel: People, context: Context) {
        idVH = peopleModel.id

        people.text = peopleModel.name
        age.text = peopleModel.age.toString() + " años"

        switch.isChecked=peopleModel.state
        switch.setOnCheckedChangeListener { _, isChecked ->
            //switch.setOnKeyListener()=peopleModel.state
            if (isChecked) {
                peopleModel.state = isChecked
                statePeople(peopleModel)
            } else {
                peopleModel.state = isChecked
                statePeople(peopleModel)
            }
        }
        if (peopleModel.number.equals("")) {//Validar el numero de celular
            number.text = "Sin numero"
        } else {
            number.text = peopleModel.number
            number.setOnClickListener {
                println("number.setOnClickListener")
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${number.text}")
                }
                context.startActivity(intent)
            }
        }
        circlePhoto()
        if (peopleModel.photo.equals("")) {//Validar foto de perfil
            Glide.with(photo.context)
                .load("https://www.ver.bo/wp-content/uploads/2019/01/4b463f287cd814216b7e7b2e52e82687.png_1805022883.png")
                .into(photo)
        } else {
            Glide.with(photo.context).load(peopleModel.photo).into(photo)
        }
    }

    fun statePeople(peopleModel: People) {
        if (peopleModel.state) {//validar estado
            state.text = "Activo"
        } else {
            state.text = "Inactivo"
        }
        db = FirebaseFirestore.getInstance()
        val poepleDB = db.collection("persona").document(idVH)
        poepleDB
            .update("estado", peopleModel.state)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

    }

    fun circlePhoto() {
        photo.apply {
            // Set Color
            circleColor = Color.WHITE
            // or with gradient
            circleColorStart = Color.WHITE
            circleColorEnd = Color.WHITE
            circleColorDirection = CircularImageView.GradientDirection.TOP_TO_BOTTOM

            // Set Border
            borderWidth = 1f
            borderColor = Color.WHITE
            // or with gradient
            borderColorStart = Color.WHITE
            borderColorEnd = Color.WHITE
            borderColorDirection = CircularImageView.GradientDirection.TOP_TO_BOTTOM

            // Add Shadow with default param
            shadowEnable = true
            // or with custom param
            shadowRadius = 1f
            shadowColor = Color.WHITE
            shadowGravity = CircularImageView.ShadowGravity.CENTER
        }
    }
}


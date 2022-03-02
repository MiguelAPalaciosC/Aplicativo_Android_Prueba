package com.example.ejercicio.adapter

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejercicio.People
import com.example.ejercicio.R
import com.mikhaellopez.circularimageview.CircularImageView

class PeopleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var photo = view.findViewById<CircularImageView>(R.id.ivPeople)
    val people = view.findViewById<TextView>(R.id.tvPeopleName)
    val age = view.findViewById<TextView>(R.id.tvAge)
    val number = view.findViewById<TextView>(R.id.tvNumber)
    val state = view.findViewById<TextView>(R.id.tvState)
    val switch=view.findViewById<Switch>(R.id.stState)

    fun render(peopleModel: People) {
        people.text = peopleModel.name
        age.text = peopleModel.age.toString() + " años"

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                peopleModel.state=isChecked
                statePeople(peopleModel)
            } else {
                peopleModel.state=isChecked
                statePeople(peopleModel)
            }
        }
        if (peopleModel.number.equals("")){//Validar el numero de celular
            number.text = "Sin numero"
        }else{
            number.text = peopleModel.number
            dialPhoneNumber(peopleModel.number)
        }
        circlePhoto()
        if (peopleModel.photo.equals("")){//Validar foto de perfil
            Glide.with(photo.context).load("https://www.ver.bo/wp-content/uploads/2019/01/4b463f287cd814216b7e7b2e52e82687.png_1805022883.png").into(photo)
        }else{
            Glide.with(photo.context).load(peopleModel.photo).into(photo)
        }
    }
    fun statePeople(peopleModel: People){
        if(peopleModel.state){//validar estado
            state.text= "Activo"
        }else{
            state.text= "Inactivo"
        }
    }

    fun circlePhoto(){
        photo.apply {
            // Set Color
            circleColor = Color.WHITE
            // or with gradient
            circleColorStart = Color.BLACK
            circleColorEnd = Color.MAGENTA
            circleColorDirection = CircularImageView.GradientDirection.TOP_TO_BOTTOM

            // Set Border
            borderWidth = 7f
            borderColor = Color.BLACK
            // or with gradient
            borderColorStart = Color.BLACK
            borderColorEnd = Color.MAGENTA
            borderColorDirection = CircularImageView.GradientDirection.TOP_TO_BOTTOM

            // Add Shadow with default param
            shadowEnable = true
            // or with custom param
            shadowRadius = 5f
            shadowColor = Color.MAGENTA
            shadowGravity = CircularImageView.ShadowGravity.CENTER
        }
    }

    fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        /*if (intent.resolveActivity(PeopleViewHolder) != null) {
            startActivity(intent)
        }*/
    }
}


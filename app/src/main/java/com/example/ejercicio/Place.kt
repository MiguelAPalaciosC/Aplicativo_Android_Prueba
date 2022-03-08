package com.example.ejercicio

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DatabaseReference

data class Place(
    val name: String,
    val latLng: LatLng
)

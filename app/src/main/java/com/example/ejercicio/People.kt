package com.example.ejercicio

import com.google.firebase.Timestamp

data class People(
    var photo: String,
    var name: String,
    var age: String,
    var number: String = DEFAULT_NUMBER,
    var state: Boolean,
    var location:String
) {
    companion object {
        private const val DEFAULT_NUMBER = "Sin numero"
    }

}
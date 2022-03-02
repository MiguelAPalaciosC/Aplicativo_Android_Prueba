package com.example.ejercicio

data class People(
    var photo: String,
    var name: String,
    var age: Int,
    var number: String = DEFAULT_NUMBER,
    var state: Boolean
) {
    companion object {
        private const val DEFAULT_NUMBER = "Sin numero"
    }
}
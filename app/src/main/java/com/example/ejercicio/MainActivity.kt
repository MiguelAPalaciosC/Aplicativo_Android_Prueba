package com.example.ejercicio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.adapter.PeopleAdapter
import com.example.ejercicio.databinding.ActivityMainBinding
import com.google.firebase.Timestamp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

private const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    //private var firebaseAnalytics = Firebase.analytics
    //private var db = firebaseAnalytics

    //lateinit var binding: ActivityMainBinding
    private val lista: ArrayList<People> = ArrayList()
    private val adapterPeople = PeopleAdapter(lista, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //PeopleProvider.peopleList
        setContentView(R.layout.activity_main)
        initRecyclerView()
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        setUpFab()
        // Obtain the FirebaseAnalytics instance.
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("persona").get().addOnCompleteListener {
            if (it.isSuccessful) {
                lista.clear()
                for (document: QueryDocumentSnapshot in it.result) {
                    val nombre: String? = document.getString("nombre")
                    val fechaNacimiento: Timestamp? =
                        document.getTimestamp("fecha_nacimiento")
                    println("----------NOmbre---------" +nombre + "---años--- " + fechaNacimiento?.toDate())
                    val contacto: String? = document.getString("contacto")
                    val foto: String? = document.getString("foto")
                    val estado: Boolean? = document.getBoolean("estado")
                    val ubicacion: String? = document.getGeoPoint("ubicacion").toString()

                    if (nombre != null && fechaNacimiento != null && estado != null && ubicacion != null) {
                        lista.add(
                            People(
                                foto.toString(),
                                nombre,
                                fechaNacimiento.toString(),
                                contacto.toString(),
                                estado,
                                ubicacion
                            )
                        )
                    }
                }
                adapterPeople.notifyDataSetChanged()
            }
        }
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
        //recyclerView.adapter = PeopleAdapter(PeopleProvider.peopleList)
        recyclerView.adapter = adapterPeople
        recyclerView.addItemDecoration(decoration)
    }

    private fun getPeopleFromFirebase() {

    }
}


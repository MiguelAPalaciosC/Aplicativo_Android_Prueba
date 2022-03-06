package com.example.ejercicio

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.adapter.PeopleAdapter
import com.google.firebase.Timestamp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

private const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : AppCompatActivity() {

    //private var firebaseAnalytics = Firebase.analytics
    //private var db = firebaseAnalytics

    //lateinit var binding: ActivityMainBinding
    private val lista: ArrayList<People> = ArrayList()
    private val adapterPeople = PeopleAdapter(lista, this)
    private var ageDate: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
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
                    if (fechaNacimiento != null) {
                        ageDate = calculateAge(fechaNacimiento)
                    }
                    println("----------NOmbre---------" + nombre + "---años--- " + (ageDate))
                    val contacto: String? = document.getString("contacto")
                    val foto: String? = document.getString("foto")
                    val estado: Boolean? = document.getBoolean("estado")
                    val ubicacion: String? = document.getGeoPoint("ubicacion").toString()

                    if (nombre != null && fechaNacimiento != null && estado != null && ubicacion != null) {
                        lista.add(
                            People(
                                foto.toString(),
                                nombre,
                                ageDate.toString(),
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAge(dateTt: Timestamp): Int {

        var datetime: Long = System.currentTimeMillis()
        var date: Long = TimeUnit.MILLISECONDS.toSeconds(datetime)
        var tt: Timestamp = Timestamp(date, 0)
        println(">>>>>>>>>>>>>>>>>" + ((tt.toDate().month) + 1))
        if (dateTt?.toDate()?.month >= tt.toDate().month && dateTt?.toDate()?.day >= tt.toDate().day) {
            var age = tt.toDate().year - dateTt?.toDate()?.year
            println(""+tt.toDate().year + "-" + dateTt?.toDate()?.year)
            return age
        } else if (dateTt?.toDate()?.month > tt.toDate().month){
            var age = tt.toDate().year - dateTt?.toDate()?.year - 1
            println(""+tt.toDate().year + "-" + dateTt?.toDate()?.year)
            return age
        }else{
            return 0;
        }
    }

    private fun setUpFab() {
        //val fab = binding.fab
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerTest)
        fab.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
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


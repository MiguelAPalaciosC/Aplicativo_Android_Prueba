package com.example.ejercicio

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio.adapter.PeopleAdapter
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_people.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

private const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

open class MainActivity : AppCompatActivity() {

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
                        //println(">>>>>>>fechaNacimiento>>>>>>>>>>" + ((fechaNacimiento.toDate().toString())))
                        ageDate = calculateAge(fechaNacimiento)
                    }

                    val contacto: String? = document.getString("contacto")
                    val foto: String? = document.getString("foto")
                    val estado: Boolean? = document.getBoolean("estado")
                    val ubicacion: GeoPoint? = document.getGeoPoint("ubicacion")
                    //println("-----------" + ubicacion?.latitude.toString())
                    /*************+Leer datos tipo Map***********/
                    val location = document.data.getValue("location") as HashMap<String, Any>

                    val lat=location.get("lat")
                    Log.d("-----------", "$lat")
                    /********************************************/
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
    private fun calculateAge(dateAge: Timestamp): Int {

        var datetime: Long = System.currentTimeMillis()
        var date: Long = TimeUnit.MILLISECONDS.toSeconds(datetime)
        var dateToday = Timestamp(date, 0)
        //println(">>>>>>>>>>>>>>>>>" + ((dateAge.toDate().toString())))
        //println("---------------------------"+dateToday.toDate().year + "-" + dateAge?.toDate()?.year+"-------------")
        if (dateAge?.toDate()?.year < dateToday.toDate().year && dateAge?.toDate()?.month <= dateToday.toDate().month) {
            if (dateAge?.toDate()?.month < dateToday.toDate().month){
                var age = dateToday.toDate().year - dateAge?.toDate()?.year
                //println(""+dateToday.toDate().year + "-" + dateAge?.toDate()?.year)
                return age
            }else if (dateAge?.toDate()?.month == dateToday.toDate().month && dateAge?.toDate()?.day <= dateToday.toDate().day){
                var age = dateToday.toDate().year - dateAge?.toDate()?.year
                //println(""+dateToday.toDate().year + "-" + dateAge?.toDate()?.year)
                return age
            }else{
                var age = dateToday.toDate().year - dateAge?.toDate()?.year - 1
                //println(""+dateToday.toDate().year + "-" + dateAge?.toDate()?.year)
                return age
            }

        } else if (dateAge?.toDate()?.year < dateToday.toDate().year){
            var age = dateToday.toDate().year - dateAge?.toDate()?.year - 1
            //println(""+dateToday.toDate().year + "-" + dateAge?.toDate()?.year)
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

}


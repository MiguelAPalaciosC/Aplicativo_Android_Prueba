package com.example.ejercicio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.ejercicio.databinding.ActivityMapsBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val listaUbication: ArrayList<Place> = ArrayList()
    private var cont:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("persona").get().addOnCompleteListener {
            if (it.isSuccessful) {
                listaUbication.clear()
                for (document: QueryDocumentSnapshot in it.result) {
                    val nombre: String? = document.getString("nombre")
                    val ubicacion: GeoPoint? = document.getGeoPoint("ubicacion")
                    //val location:MutableMap<String,String>=HashMap()
                    println("-----------" + ubicacion?.latitude.toString())
                    if ( ubicacion != null) {
                        cont++
                        listaUbication.add(
                            Place(
                                nombre.toString(),
                                LatLng(ubicacion.latitude,ubicacion.longitude)
                            )
                        )
                    }
                }
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync { googleMap ->
                    addMarkers(googleMap)
                }
                fabMap.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        println("Contador -------->>>>>>>>>"+cont)
        tvNumeroPuntos.text= "Numero de puntos generados: " +listaUbication.size

    }

    private fun addMarkers(googleMap: GoogleMap) {
        mMap = googleMap
        println("-----addMarkers------")
        listaUbication.forEach { place ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .position(place.latLng)
            )
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(marker))
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        /*for (locations in listaUbication){
            val lctn = LatLng(locations.location.latitude, locations.location.longitude)
            mMap.addMarker(MarkerOptions().position(lctn).title("Ubicacion de: "+locations.name))
            println("--------------->>>>>>>>>><<"+locations.name)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lctn))
        }*/
    }

}
package com.mg.iqlance_practical.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.SupportMapFragment

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.android.gms.maps.OnMapReadyCallback
import com.mg.iqlance_practical.R
import com.google.android.gms.maps.CameraUpdateFactory

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Address
import android.location.Geocoder
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.FragmentActivity
import com.mg.iqlance_practical.Constant
import com.mg.iqlance_practical.Student
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException

import android.R.attr.x
import android.R.attr.y
import android.graphics.BitmapFactory
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.android.gms.maps.model.*


class MapsActivity : FragmentActivity(), OnMapReadyCallback {


    private var nearByStdList: java.util.ArrayList<LatLng>? = null
    private var data: Student? = null
    private var currentlatlng: LatLng? = null
    private var mMap: GoogleMap? = null

    lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        currentlatlng = intent.getParcelableExtra("latlong") as? LatLng
        data = intent.getSerializableExtra("data") as? Student
        nearByStdList = intent.getSerializableExtra("nearByStdList") as? ArrayList<LatLng>


        val mapFragment = supportFragmentManager
            .findFragmentById(com.mg.iqlance_practical.R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                var location: String = searchView.query.toString()
                var list: List<Address> = emptyList()

                if (location != null) {

                    val geocoder = Geocoder(applicationContext)
                    try {
                        list = geocoder.getFromLocationName(location, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    if (list.size > 0) {
                        var address = list.get(0)
                        var latLng = LatLng(address.latitude, address.longitude)
                        mMap?.addMarker(MarkerOptions().position(latLng).title(location))
                        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))

                        mMap?.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    latLng.latitude,
                                    latLng.longitude
                                ), 12.0f
                            )
                        )

                        mMap?.setOnMarkerClickListener { marker ->

                            Constant.address = marker.title

                            finish()

                            true
                        }


                    }
                }


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }


    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap

        if (currentlatlng != null) {
            searchView.visibility = View.GONE
            mMap?.addMarker(MarkerOptions().position(currentlatlng!!).title(data?.address))

            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentlatlng, 100F))

            mMap?.addCircle(
                CircleOptions().center(
                    LatLng(
                        currentlatlng!!.latitude,
                        currentlatlng!!.longitude
                    )
                )
                    .radius(1000.0)
                    .strokeWidth(0f)
                    .fillColor(0x550000FF)
            )

            for (i in nearByStdList!!.indices) {

                mMap?.addMarker(MarkerOptions().position(nearByStdList!!.get(i)))
                    ?.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            }
        }

    }
}

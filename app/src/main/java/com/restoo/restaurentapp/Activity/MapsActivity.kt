package com.restoo.restaurentapp.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.restoo.restaurentapp.R
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.util.*
import kotlin.collections.ArrayList


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {


    private var mMap: GoogleMap? = null
    var currentmarker : Marker? = null
    internal lateinit var mLastLocation: Location
    var currentlocation : Location? = null
    internal lateinit var mLocationResult: LocationRequest
    internal lateinit var mLocationCallback: LocationCallback
    internal var mCurrLocationMarker: Marker? = null
    internal var mGoogleApiClient: GoogleApiClient? = null
    internal lateinit var mLocationRequest: LocationRequest
    internal var mFusedLocationClient: FusedLocationProviderClient? = null
    var totlaMarkers :ArrayList<LatLng>? = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission()

    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1000)
            return
        }

        val task = mFusedLocationClient?.lastLocation
        task?.addOnSuccessListener { location ->
            if (location != null) {
                currentlocation = location
                val mapFragment = supportFragmentManager
                        .findFragmentById(R.id.map) as SupportMapFragment?
                mapFragment!!.getMapAsync(this)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1000 -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationPermission()
            }
        }
    }




    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val  latlng = LatLng(currentlocation?.latitude!!, currentlocation?.longitude!!)
        totlaMarkers?.add(latlng)
        Drawmarker(latlng)




        mMap?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(p0: Marker) {

            }

            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                if (currentmarker != null) {
                    // currentmarker!!.remove()
                    val newlatlng = LatLng(p0.position.latitude, p0.position.longitude)
                    totlaMarkers?.add(newlatlng)
                    Drawmarker(newlatlng)
                }
            }
        })
    }

    fun getDirectionUrl(origin: LatLng, dest: LatLng) : String {

        val str_origin = "origin=" + origin.latitude.toString() + "," + origin.longitude

        val str_dest = "destination=" + dest.latitude.toString() + "," + dest.longitude

        val sensor = "sensor=false"
        val mode = "mode=driving"

        val parameters = "$str_origin&$str_dest&$sensor&$mode"

        val output = "json"
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters

    }

    inner class getDirection(var url: String) : AsyncTask<Void, Void, List<List<LatLng>>> () {

       override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
           val request = Request.Builder().url(url).build()
           val response = client.newCall(request).execute()
           val data = response.body().toString()
           val result = ArrayList<List<LatLng>>()
           try {
               var path = ArrayList<LatLng>()
               path.add(LatLng(totlaMarkers?.get(0)?.latitude!!, totlaMarkers?.get(0)?.longitude!!))
               path.add(LatLng(totlaMarkers?.get(1)?.latitude!!, totlaMarkers?.get(1)?.longitude!!))

               result.add(path)

           } catch (e: Exception) {

           }
           return  result
        }

        override fun onPostExecute(result: List<List<LatLng>>?) {
            super.onPostExecute(result)
            val lineoptions = PolylineOptions()
            for (i in result?.indices!!) {
                lineoptions.addAll(result[i])
                lineoptions.width(10f)
                lineoptions.color(Color.RED)
                lineoptions.geodesic(true)
            }
            mMap?.addPolyline(lineoptions)
        }
    }


    private fun Drawmarker(latlng: LatLng) {
        val markeroption = MarkerOptions().position(latlng).title("my position").snippet(getAddress(latlng.latitude, latlng.longitude)).draggable(true)

        mMap?.animateCamera(CameraUpdateFactory.newLatLng(latlng))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
        currentmarker = mMap?.addMarker(markeroption)
        currentmarker?.showInfoWindow()

//        if (totlaMarkers?.size!! >1) {
//            val line = PolylineOptions().add(LatLng(totlaMarkers!!.get(0).latitude,
//                    totlaMarkers!!.get(0).longitude),
//                    LatLng(totlaMarkers!!.get(1).latitude,
//                            totlaMarkers!!.get(1).longitude))
//                    .width(5f).color(Color.RED)
//
//            mMap?.addPolyline(line)
//            totlaMarkers = ArrayList()
//        }

        if (totlaMarkers?.size == 2) {
            val url = getDirectionUrl(totlaMarkers?.get(0)!!, totlaMarkers?.get(1)!!)
            getDirection(url).execute()
            totlaMarkers = ArrayList()
        }
    }

    private fun getAddress(latitude: Double, longitude: Double): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        val address= geocoder.getFromLocation(latitude, longitude, 1)
        return address[0].getAddressLine(0).toString()

    }

//    fun getDirectionUrl(orgin : LatLng, destination : LatLng) : String {
//        return
//    }
}
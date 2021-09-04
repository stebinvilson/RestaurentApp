package com.restoo.restaurentapp.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.restoo.restaurentapp.R
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var origin: MarkerOptions? = MarkerOptions()
    var destination:MarkerOptions? = MarkerOptions()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null
    private var likelyPlaceNames: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAddresses: Array<String?> = arrayOfNulls(0)
    private var likelyPlaceAttributions: Array<List<*>?> = arrayOfNulls(0)
    private var likelyPlaceLatLngs: Array<LatLng?> = arrayOfNulls(0)

    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
//        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
//        placesClient = Places.createClient(this)

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        //Setting marker to draw route between these two points
        origin = MarkerOptions().position(LatLng(12.9121, 77.6446)).title("HSR Layout").snippet("origin")
        destination = MarkerOptions().position(LatLng(12.9304, 77.6784)).title("Bellandur").snippet("destination")


    }

//    fun getDocument(start: LatLng, end: LatLng, mode: String?): Document? {
//        val url = ("http://maps.googleapis.com/maps/api/directions/xml?"
//                + "origin=" + start.latitude + "," + start.longitude
//                + "&destination=" + end.latitude + "," + end.longitude
//                + "&sensor=false&units=metric&mode=driving")
//        try {
//            val httpClient: HttpClient = DefaultHttpClient()
//            val localContext: HttpContext = BasicHttpContext()
//            val httpPost = HttpPost(url)
//            val response: HttpResponse = httpClient.execute(httpPost, localContext)
//            val `in`: InputStream = response.getEntity().getContent()
//            val builder: DocumentBuilder = DocumentBuilderFactory.newInstance()
//                    .newDocumentBuilder()
//            return builder.parse(`in`)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return null
//    }

    companion object {
        private val TAG = MapsActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        // Keys for storing activity state.
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

        // Used for selecting the current place.
        private const val M_MAX_ENTRIES = 5
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        mMap.addMarker(origin)
//        mMap.addMarker(destination)
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin?.getPosition(), 10f))

        updateLocationUI()

        // Get the current location of the device and set the position of the map.
        getDeviceLocation()


//        mMap.setOnMarkerClickListener(OnMarkerClickListener { arg0 -> // Creating a marker
//            val markerOptions = MarkerOptions()
//
//            // Setting the position for the marker
//            markerOptions.position(arg0.position) // get Latlong
//            // Now you use above logic
//            true
//        })
//        var doc: Document? = getDocument(origin!!.position, destination!!.position,
//                "driving")
//
//        val directionPoint: ArrayList<LatLng>? = doc?.let { getDirection(it) }
//        val rectLine = PolylineOptions().width(3f).color(
//                Color.RED)
//
//        if (directionPoint != null) {
//            for (i in 0 until directionPoint.size) {
//                rectLine.add(directionPoint[i])
//            }
//        }
//        val polylin = mMap.addPolyline(rectLine)

//        rectLine.add(origin?.position)
//        rectLine.add(destination?.position)
//        val polylin = mMap.addPolyline(rectLine)

    }


    private fun updateLocationUI() {
        if (mMap == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                mMap?.isMyLocationEnabled = true
                mMap?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                mMap?.isMyLocationEnabled = false
                mMap?.uiSettings?.isMyLocationButtonEnabled = false
               // lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    LatLng(lastKnownLocation!!.latitude,
                                            lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))

                            var lat : LatLng = LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude)
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lat, 15.0f))
                            mMap.addMarker(MarkerOptions().position(lat).title("my location"))

                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        mMap?.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        mMap?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    fun getDirection(doc: Document): ArrayList<LatLng>? {
        val nl1: NodeList
        var nl2: NodeList
        var nl3: NodeList
        val listGeopoints = ArrayList<LatLng>()
        nl1 = doc.getElementsByTagName("step")
        if (nl1.getLength() > 0) {
            for (i in 0 until nl1.getLength()) {
                val node1: Node = nl1.item(i)
                nl2 = node1.getChildNodes()
                var locationNode: Node = nl2
                        .item(getNodeIndex(nl2, "start_location"))
                nl3 = locationNode.getChildNodes()
                var latNode: Node = nl3.item(getNodeIndex(nl3, "lat"))
                var lat: Double = latNode.getTextContent().toDouble()
                var lngNode: Node = nl3.item(getNodeIndex(nl3, "lng"))
                var lng: Double = lngNode.getTextContent().toDouble()
                listGeopoints.add(LatLng(lat, lng))
                locationNode = nl2.item(getNodeIndex(nl2, "polyline"))
                nl3 = locationNode.getChildNodes()
                latNode = nl3.item(getNodeIndex(nl3, "points"))
                val arr: ArrayList<LatLng> = decodePoly(latNode.getTextContent())!!
                for (j in 0 until arr.size) {
                    listGeopoints.add(LatLng(arr[j].latitude, arr[j].longitude))
                }
                locationNode = nl2.item(getNodeIndex(nl2, "end_location"))
                nl3 = locationNode.getChildNodes()
                latNode = nl3.item(getNodeIndex(nl3, "lat"))
                lat = latNode.getTextContent().toDouble()
                lngNode = nl3.item(getNodeIndex(nl3, "lng"))
                lng = lngNode.getTextContent().toDouble()
                listGeopoints.add(LatLng(lat, lng))
            }
        }
        return listGeopoints
    }

    private fun decodePoly(encoded: String): ArrayList<LatLng>? {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val position = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(position)
        }
        return poly
    }

    private fun getNodeIndex(nl: NodeList, nodename: String): Int {
        for (i in 0 until nl.length) {
            if (nl.item(i).nodeName == nodename) return i
        }
        return -1
    }

    fun getDocument(start: LatLng, end: LatLng, mode: String): Document? {
//        val url = ("http://maps.googleapis.com/maps/api/directions/xml?"
//                + "origin=" + start.latitude + "," + start.longitude
//                + "&destination=" + end.latitude + "," + end.longitude
//                + "&sensor=false&units=metric&mode=driving")
//
//        try {
//            val httpClient: HttpClient = DefaultHttpClient()
//            val localContext: HttpContext = BasicHttpContext()
//            val httpPost = HttpPost(url)
//            val response: HttpResponse = httpClient.execute(httpPost, localContext)
//            val `in`: InputStream = response.entity.content
//            val builder: DocumentBuilder = DocumentBuilderFactory.newInstance()
//                    .newDocumentBuilder()
//            return builder.parse(`in`)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
        return null
    }

    private fun getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

}
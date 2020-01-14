package fr.hetic.app_map_amis

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.drawable.VectorDrawable
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_choose_place.*

/*

class ChoosePlace : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_place)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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

        // just a random location our map will point to when its launched
        val klcc = LatLng(48.8534, 2.3488)
        mMap.addMarker(MarkerOptions().apply {
            position(klcc)
            title("Marker pointed at klcc")
            draggable(false)
        })
        // setup zoom level
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(klcc,18f))


    }

    fun onCameraMove() {
        mMap.clear()
        // display imageView
        imgPinUp?.visibility = View.VISIBLE
    }

    fun onCameraIdle() {
        // hiding imageView
        imgPinUp?.visibility = View.GONE
        // customizing map marker with a custom icon
        // and place it on the current map camera position
        val markerOptions = MarkerOptions().position(mMap.cameraPosition.target)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pinsvg))
        mMap.addMarker(markerOptions)

    }
}

*/

class ChoosePlace : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnCameraMoveCanceledListener,
    GoogleMap.OnCameraIdleListener{



    private val REQUEST_CHECK_SETTINGS: Int=101;
    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=101;

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient;
    private lateinit var lastLocation: Location;
    private lateinit var locationRequest: LocationRequest;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_place)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map2) as SupportMapFragment


        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(
                    this.getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                mMap.isMyLocationEnabled = true;
                checkLocationService();
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                );
            }
        } else
        {
            mMap.isMyLocationEnabled = true;
            checkLocationService();
        }

        mMap.setOnCameraMoveStartedListener(this)
        mMap.setOnCameraIdleListener(this)
        mMap.setOnCameraMoveListener(this)

    }

    private val PERMISSION_LOCATION_REQUEST_CODE = 206

    private lateinit var locationService: FusedLocationProviderClient

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            /* val lastLocation = locationResult?.lastLocation

            if(lastLocation is Location) {
                val text = "${locationTextView.text} \n" +
                        "New Position = latitude: ${lastLocation.latitude}, longitude: ${lastLocation.longitude}"
                locationTextView.text = text
            }*/
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==PERMISSION_LOCATION_REQUEST_CODE) {
            if(permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION
                && grantResults[0]==PackageManager.PERMISSION_GRANTED) {

                // L'utilisateur a accepté la permission pour la géolocalisation

                val locationRequest = LocationRequest()
                locationRequest.fastestInterval = 5000
                locationRequest.interval = 10000
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

                locationService.requestLocationUpdates(locationRequest, locationCallback, null)
            }
        }
    }

    fun fetchCurrentLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }

    fun checkLocationService() {

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(2 * 1000);


        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        // builder.setAlwaysShow(true);
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener(this) {it->
            it.locationSettingsStates;
            fetchCurrentLocation();
        }

        task.addOnFailureListener(this) { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(
                        this@ChoosePlace,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }

            }
        }
    }
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                val result = data!!.getStringExtra("result")
                fetchCurrentLocation();
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }




    override fun onCameraMoveStarted(p0: Int) {
        Log.v("Onmove start","Onmove "+p0)
    }

    override fun onCameraMove() {
        // display imageView
        imgPinUp?.visibility = View.VISIBLE
        mMap.clear()

        Log.v("Onmove ","Onmove ");
    }

    override fun onCameraMoveCanceled() {
        Log.v("Onmove cancel","Onmove ");
    }

    override fun onCameraIdle() {
        Log.v("Onmove Idle","Onmove ");

        // hiding imageView
        imgPinUp?.visibility = View.GONE

        // customizing map marker with a custom icon
        // and place it on the current map camera position
        val markerOptions = MarkerOptions().position(mMap.cameraPosition.target)
        mMap.addMarker(markerOptions)

    }

}


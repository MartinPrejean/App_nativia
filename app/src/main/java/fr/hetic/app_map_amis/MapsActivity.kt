package fr.hetic.app_map_amis

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import kotlinx.android.synthetic.main.fragment_custom.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

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

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationService = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest()
        locationRequest.fastestInterval = 5000
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
            }

        // Vérification des permissions

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            ActivityCompat
                .requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_LOCATION_REQUEST_CODE)
        } else {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }

        btnStartSearchPlace.setOnClickListener {
            val intent = Intent(this, ChoosePlace::class.java)
            // start your next activity
            startActivity(intent)
        }

    }


    override fun onDestroy() {
        super.onDestroy()

        locationService.removeLocationUpdates(locationCallback)
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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMyLocationEnabled(true)

        // Add a marker in Paris and move the camera
        /*
        val paris = LatLng(48.8534, 2.3488)
        mMap.addMarker(MarkerOptions().position(paris).title("Marker in Paris"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(paris))
        */
    }


    /*
    //SAVE USERS UPDATED LOCATION TO THE DATABASE
    if (currentUser != null) {
        fun onLocationChanged(location: Location) {
        mLastLocation = location
        val latLng = LatLng(location.latitude, location.longitude)
        val userID: String = FirebaseAuth.getInstance().getCurrentUser().getUid()
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Geo")

        //ADD THE UPDATED USER LAT/LNG TO THE DATABASE
        val geoFire = GeoFire(ref)
        geoFire.setLocation(userID, GeoLocation(location.latitude, location.longitude))
    }
    }

    */


}

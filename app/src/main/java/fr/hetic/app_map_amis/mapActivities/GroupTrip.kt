package fr.hetic.app_map_amis.mapActivities

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import fr.hetic.app_map_amis.R

class GroupTrip : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location;
    private val REQUEST_CHECK_SETTINGS: Int=101;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_trip)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        /*btnStartSearchPlace.setOnClickListener {
            val intent = Intent(this, ApiCall::class.java)
            // start your next activity
            startActivity(intent)
        }*/

    }


    private lateinit var fusedLocationClient: FusedLocationProviderClient;

    //Recupération de l'id du trajet
    /*intent?.let{
        user = intent.extras.getParcelable(ChoosePlace.USER) as User
    }

    printData(user)*/

    //Recupère données du trajet
    /*
    fun printData(user: User)
    {
        lateinit var tripList: ListView

        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Trajet")
        val list: List<Any>
        var i = 0

        ref.child(user.toString())addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
     */


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

    private lateinit var locationRequest: LocationRequest;

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
                        this@GroupTrip,
                        REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    /*
                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0!!.exists()) {
                            for (h in p0.children) {
                                val trip = h.getValue(Trip::class.java)
                                tripList.add(trip)
                            }

                            var adapter =
                                ListAdapter(applicationContext, R.layout.activity_trip, tripList)
                            listview.adapter = adapter
                        }
                    }
                    */
                }

            }
        }
    }

    var PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=101

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(
                    this.getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                mMap.isMyLocationEnabled = true;
                checkLocationService();
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
                );
            }
        } else
        {
            mMap.isMyLocationEnabled = true;
            checkLocationService();
        }

    }
}

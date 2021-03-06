package fr.hetic.app_map_amis.mapActivities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import fr.hetic.app_map_amis.ActivityListContact
import fr.hetic.app_map_amis.R
import fr.hetic.app_map_amis.data.Localisation
import fr.hetic.app_map_amis.data.User
import kotlinx.android.synthetic.main.activity_choose_place.*
import kotlin.properties.Delegates


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

    private var latitudeMarker by Delegates.notNull<Double>()
    private var longitudeMarker by Delegates.notNull<Double>()

    companion object{
        const val USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_place)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map2) as SupportMapFragment


        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        var button: Button = findViewById(R.id.btnConfirmPosition)
        button.setOnClickListener{
            var id = sendLocalisation(latitudeMarker, longitudeMarker)
            val user: User =
                User(id)
            val intent = Intent(this, ActivityListContact::class.java)
            intent.putExtra(USER, user)
            // intent.putExtra(LOCALISATION, latitudeMarker)
            // intent.putExtra(LOCALISATION, longitudeMarker)
            startActivity(intent)
        }
    }

    fun sendLocalisation(latitude: Double, longitude: Double): String{

        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Trajet")

        //locaId = id group
        var locaId : String? = ref.push().key
        var loca : Localisation =
            Localisation(
                locaId!!,
                latitude,
                longitude
            )

        ref.child(locaId!!).setValue(loca).addOnCompleteListener {
            //Toast.makeText(applicationContext, "Saved successfully", Toast.LENGTH_LONG).show()
        }

        return locaId
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
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

        mMap.setOnCameraMoveStartedListener(this)
        mMap.setOnCameraIdleListener(this)
        mMap.setOnCameraMoveListener(this)

    }

    private val PERMISSION_LOCATION_REQUEST_CODE = 206

    private lateinit var locationService: FusedLocationProviderClient

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {

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

    override fun onCameraIdle(){
        Log.v("Onmove Idle","Onmove ");

        // hiding imageView
        imgPinUp?.visibility = View.GONE

        // customizing map marker with a custom icon
        // and place it on the current map camera position
        val markerOptions = MarkerOptions().position(mMap.cameraPosition.target)
        mMap.addMarker(markerOptions)

        latitudeMarker = mMap.cameraPosition.target.latitude
        longitudeMarker = mMap.cameraPosition.target.longitude

    }

}


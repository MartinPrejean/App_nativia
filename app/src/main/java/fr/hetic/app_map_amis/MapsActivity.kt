package fr.hetic.app_map_amis

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_custom.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        btnStartSearchPlace.setOnClickListener {
            val intent = Intent(this, ChoosePlace::class.java)
            // start your next activity
            startActivity(intent)
        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMyLocationEnabled(true)

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

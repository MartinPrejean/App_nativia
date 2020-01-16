package fr.hetic.app_map_amis.mapActivities

import android.os.Bundle
import android.widget.ListAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import fr.hetic.app_map_amis.R
import fr.hetic.app_map_amis.data.Trip

class GroupTrip : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var user: User
    lateinit var tripList: List<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_trip)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        /*intent?.let{
            user = intent.extras.getParcelable(ChoosePlace.USER) as User
        }*/

        //printData()
    }

    fun printData()
    {
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Trajet")
        val list: List<Any>
        var i = 0


        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }
            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    for(h in p0.children){
                        val trip = h.getValue(Trip::class.java)
                        //tripList.add(trip!!)
                    }

                    //var adapter = ListAdapter(applicationContext, R.layout.activity_trip, tripList)
                    //listview.adapter = adapter
                }
            }
        })

    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        // val sydney = LatLng(-34.0, 151.0)
        // mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}

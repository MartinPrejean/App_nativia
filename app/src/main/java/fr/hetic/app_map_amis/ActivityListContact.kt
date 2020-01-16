package fr.hetic.app_map_amis

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_hour_fix.*
import kotlinx.android.synthetic.main.activity_list_contact.*

class ActivityListContact : AppCompatActivity() {

    var tripList = mutableListOf<trip>()
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_contact)

        intent?.let{
            val user = intent.extras.getParcelable(ChoosePlace.USER) as User
            textView3.text = user.toString()
        }

        var button: Button = findViewById(R.id.btnConfirmPosition)
        ref = FirebaseDatabase.getInstance().getReference("trip")
        button.setOnClickListener{
            //saveTrip()
        }


        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    for(h in p0.children){
                        val trip = h.getValue(trip::class.java)
                        tripList.add(trip!!)
                    }

                    val adapter = ListAdapter(applicationContext, R.layout.activity_trip, tripList)
                    listview.adapter = adapter
                }
            }
        })*/

    }
}

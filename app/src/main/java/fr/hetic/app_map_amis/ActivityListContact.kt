package fr.hetic.app_map_amis

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_hour_fix.*
import kotlinx.android.synthetic.main.activity_hour_fix.textView4
import kotlinx.android.synthetic.main.activity_list_contact.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_trip.*
import kotlinx.android.synthetic.main.contact_group_layout.*

class ActivityListContact : AppCompatActivity() {

    var tripList = mutableListOf<Trip>()
    lateinit var ref: DatabaseReference
    lateinit var user: User

    companion object{
        const val USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_contact)

        intent?.let{
            user = intent.extras.getParcelable(ChoosePlace.USER) as User
        }

        /*checkBox.setOnCheckedChangeListener { buttonView, isChecked ->

        }*/

        btnSendFinal.setOnClickListener{
            val intent = Intent(this, HourFixActivity::class.java)
            intent.putExtra(ChoosePlace.USER, user)
            startActivity(intent)
        }

    }
        fun sendLocalisation(latitude: Double, longitude: Double): String{

            val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Trajet")

            //locaId = id group
            var locaId : String? = ref.push().key
            var loca : Localisation = Localisation(locaId!!, latitude, longitude)

            ref.child(locaId).setValue(loca).addOnCompleteListener {
                Toast.makeText(applicationContext, "Saved successfully", Toast.LENGTH_LONG).show()
            }

            return locaId
        }
}

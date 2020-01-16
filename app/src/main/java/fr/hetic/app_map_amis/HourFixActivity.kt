package fr.hetic.app_map_amis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_hour_fix.*

class HourFixActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hour_fix)

        var button: Button = findViewById(R.id.btnConfirmPosition)
        button.setOnClickListener{
            sendTime()
            val intent = Intent(this, ActivityListContact::class.java)
            startActivity(intent)
        }


    }
    fun sendTime(){

        /*val day = day_text.text.toString()
        val month = month_text.text.toString()
        val hour = hour_text.text.toString()
        val min = min_text.text.toString()

        val jour = day + " " + month
        val time = hour + " " + min


        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        //locaId = id group
        /*var locaId : String? = ref.push().key
        var loca : Localisation = Localisation(locaId!!, latitude, longitude)

        ref.child(locaId!!).setValue(loca).addOnCompleteListener {
            Toast.makeText(applicationContext, "Saved successfully", Toast.LENGTH_LONG).show()
        }

        return locaId*/
    }

         }
}

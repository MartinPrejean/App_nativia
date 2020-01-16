package fr.hetic.app_map_amis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import fr.hetic.app_map_amis.data.Time
import fr.hetic.app_map_amis.data.User
import fr.hetic.app_map_amis.mapActivities.ChoosePlace
import fr.hetic.app_map_amis.mapActivities.GroupTrip
import kotlinx.android.synthetic.main.activity_hour_fix.*


class HourFixActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hour_fix)
        lateinit var user: User


        intent?.let{
            user = intent.extras.getParcelable(ChoosePlace.USER) as User
        }

        var button: Button = findViewById(R.id.btnConfirmRdv)
        button.setOnClickListener{
            sendTime(user)
            val intent = Intent(this, GroupTrip::class.java)
            startActivity(intent)
        }


    }
    fun sendTime(user: User){

        val day = day_text.text.toString()
        val month = month_text.text.toString()
        val hour = hour_text.text.toString()
        val min = min_text.text.toString()

        val jour = day + " " + month
        val temps = hour + " " + min


        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("Trajet")

        //locaId = id group
        var time: Time =
            Time(jour, temps)

        ref.child(user.toString()).setValue(time).addOnCompleteListener {
            Toast.makeText(applicationContext, "Saved successfully", Toast.LENGTH_LONG).show()
        }
    }

}
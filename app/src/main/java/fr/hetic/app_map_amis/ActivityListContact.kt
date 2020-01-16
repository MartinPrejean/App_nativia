package fr.hetic.app_map_amis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hour_fix.*
import kotlinx.android.synthetic.main.activity_list_contact.*

class ActivityListContact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_contact)

        intent?.let{
            val user = intent.extras.getParcelable(ChoosePlace.USER) as User
            textView3.text = user.toString()
        }

    }
}

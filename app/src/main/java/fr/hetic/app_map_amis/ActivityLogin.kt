package fr.hetic.app_map_amis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import fr.hetic.app_map_amis.network.JourneyService
import fr.hetic.app_map_amis.network.response.JourneyResult
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityLogin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var journeyService: JourneyService

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser :FirebaseUser? = auth.currentUser
        updateUI(currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            save_user()
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.navitia.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val navitiaApiKey = "0dbdc129-2b4f-4827-b826-9379293b5869"

        journeyService = retrofit.create(JourneyService::class.java)

        journeyService.getJourney(navitiaApiKey).enqueue(object: Callback<JourneyResult> {
            override fun onFailure(call: Call<JourneyResult>, t: Throwable) {
                t.toString()
            }

            override fun onResponse(call: Call<JourneyResult>, response: Response<JourneyResult>) {
                response.body()
            }

        })
    }

        private fun save_user(){
            if(id_login.text.toString().isEmpty()){
                id_login.error = "Please enter a address mail or phone number"
                id_login.requestFocus()
                return

            }
            /*if(!Patterns.EMAIL_ADDRESS.matcher(id_login.text.toString().matches())){
                id_login.error = "Please enter valid email ou phone number"
                id_login.requestFocus()
                return
            }*/
            if(user_login.text.toString().isEmpty()){
                user_login.error = "Please enter pseudo"
                user_login.requestFocus()
                return
            }

            /*PhoneAuthProvider.getInstance().verifyPhoneNumber(
                id_login, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                this, // Activity (for callback binding)
                callbacks) // OnVerificationStateChangedCallbacks*/

            auth.createUserWithEmailAndPassword(id_login.text.toString(), user_login.text.toString())
                .addOnCompleteListener(this) { task ->

                    val TAG = "MyMessage"
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                }
        }

    fun updateUI(currentUser: FirebaseUser?)
    {

    }
}
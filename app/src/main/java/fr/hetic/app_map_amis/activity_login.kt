package fr.hetic.app_map_amis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

class activity_login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            save_user()
        }

        fun onStart() {
            super.onStart()
            // Check if user is signed in (non-null) and update UI accordingly.
            val currentUser :FirebaseUser? = auth.currentUser
            updateUI(currentUser)
        }
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

            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

    fun updateUI(currentUser: FirebaseUser?)
    {

    }
}
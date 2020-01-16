package fr.hetic.app_map_amis

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.hetic.app_map_amis.api.APIHandler
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCall : AppCompatActivity() {

    // Initialize the APIHandler class with the right context
    private val apiHandler = APIHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.content_api_call)

        // Example: select an existing textview with the id "text"
        val txt = findViewById<TextView>(R.id.textAPI)

        // Example: fetch data from specified URL and render on the textview
        // You just need to edit line 24 to do whatever you want with the "data" object :)
        apiHandler.get("https://api.navitia.io/v1/coverage/fr-nw/journeys?from=2.32157%3B48.88003&to=2.420257%3B48.85182&") {
                data: JSONObject -> txt.text = data.toString()
        }
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

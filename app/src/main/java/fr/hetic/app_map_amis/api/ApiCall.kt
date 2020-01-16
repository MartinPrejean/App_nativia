package fr.hetic.app_map_amis.api

import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.hetic.app_map_amis.R
import fr.hetic.app_map_amis.data.Localisation
import fr.hetic.app_map_amis.network.JourneyService
import fr.hetic.app_map_amis.network.response.Durations
import fr.hetic.app_map_amis.network.response.JourneyResult
import kotlinx.android.synthetic.main.content_api_call.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCall : AppCompatActivity() {

    private lateinit var journeyService: JourneyService

    private lateinit var lastLocation: Location
    private lateinit var markerLocation: Localisation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.content_api_call)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.navitia.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val navitiaApiKey = "0dbdc129-2b4f-4827-b826-9379293b5869"


        // Call API avec les coordonnées du Marker + la position de l'utilisateur.
        // Mais on a pas eu le temps (et le talent) de pouvoir transférer un objet
        // d'une variable d'une activité à une autre.
        /*
        journeyService = retrofit.create(JourneyService::class.java)

        journeyService.getJourney(lastLocation.longitude, lastLocation.latitude, markerLocation.longitude, markerLocation.latitude,navitiaApiKey).enqueue(object:Callback<JourneyResult> {
            override fun onFailure(call: Call<JourneyResult>, t: Throwable) {
                t.toString()
            }

            override fun onResponse(call: Call<JourneyResult>, response: Response<JourneyResult>) {
                response.body()
            }

        })
         */

        journeyService = retrofit.create(JourneyService::class.java)

        journeyService.getJourney(navitiaApiKey).enqueue(object: Callback<JourneyResult> {
            override fun onFailure(call: Call<JourneyResult>, t: Throwable) {
                t.toString()
            }

            override fun onResponse(call: Call<JourneyResult>, response: Response<JourneyResult>) {
                response.body()
            }

        })

        textApiDuration.text = Durations.total
    }
}

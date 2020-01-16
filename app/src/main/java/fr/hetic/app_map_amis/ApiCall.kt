package fr.hetic.app_map_amis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.hetic.app_map_amis.network.JourneyService
import fr.hetic.app_map_amis.network.response.JourneyResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCall : AppCompatActivity() {

    private lateinit var journeyService: JourneyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.content_api_call)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.navitia.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val navitiaApiKey = "0dbdc129-2b4f-4827-b826-9379293b5869"

        journeyService = retrofit.create(JourneyService::class.java)

        journeyService.getJourney(navitiaApiKey).enqueue(object:Callback<JourneyResult> {
            override fun onFailure(call: Call<JourneyResult>, t: Throwable) {
                t.toString()
            }

            override fun onResponse(call: Call<JourneyResult>, response: Response<JourneyResult>) {
                response.body()
            }

        })
    }
}

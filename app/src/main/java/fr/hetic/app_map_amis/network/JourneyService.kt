package fr.hetic.app_map_amis.network

import fr.hetic.app_map_amis.network.response.JourneyResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JourneyService {

    // https://api.navitia.io/v1/coverage/fr-nw/journeys?
    // %3B = ;
    @GET("from=2.3749036%3B48.8467927&to=2.2922926%3B48.8583736&")
    fun getJourney(@Query("query") query: String): Call<JourneyResult>

}
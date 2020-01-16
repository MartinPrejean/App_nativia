package fr.hetic.app_map_amis.network

import fr.hetic.app_map_amis.network.response.JourneyResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface JourneyService {

    // https://api.navitia.io/v1/coverage/fr-nw/journeys?from=2.3749036%3B48.8467927&to=2.2922926%3B48.8583736&
    // %3B = ;
    @GET("coverage/fr-nw/journeys?from={fromLng}%3B{fromLat}&to={toLng}%3B{toLat}&")
    fun getJourney(
        @Path("fromLng") fromLng:Double,
        @Path("fromLat") fromLat:Double,
        @Path("toLng") toLng:Double,
        @Path("toLat") toLat:Double,
        @Header("Authorization") authorization:String): Call<JourneyResult>

}
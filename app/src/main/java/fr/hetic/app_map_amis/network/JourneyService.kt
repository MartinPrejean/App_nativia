package fr.hetic.app_map_amis.network

import fr.hetic.app_map_amis.network.response.JourneyResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

// Pour les query dynamiques mais nous n'avons pas eu le temps de les implémenter
/*
interface JourneyService {

    // https://api.navitia.io/v1/coverage/fr-nw/journeys?from=2.3749036%3B48.8467927&to=2.2922926%3B48.8583736&
    // %3B = ;
    @GET("coverage/fr-nw/journeys?from={fromLng}%3B{fromLat}&to={toLng}%3B{toLat}&")
    fun getJourney(
        @Query("fromLng") fromLng:Double, 48.880315
        @Query("fromLat") fromLat:Double, 2.321609
        @Query("toLng") toLng:Double, 48.851850
        @Query("toLat") toLat:Double, 2.420295
        @Header("Authorization") authorization:String): Call<JourneyResult>

}
 */

// Coordonnées implémentées en dur dans la requête

interface JourneyService {

    // https://api.navitia.io/v1/coverage/fr-nw/journeys?from=2.3749036%3B48.8467927&to=2.2922926%3B48.8583736&
    // %3B = ;
    @GET("coverage/fr-nw/journeys?from=2.321608%3B48.880314&to=2.420294%3B48.851851&")
    fun getJourney(
        @Header("Authorization") authorization:String): Call<JourneyResult>
}
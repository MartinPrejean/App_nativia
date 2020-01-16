package fr.hetic.app_map_amis.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


// JSON Classes
@Parcelize data class Durations(
    val taxi:Int,
    val walking:Int,
    val car:Int,
    val ridesharing:Int,
    val bike:Int,
    val total:Int
) : Parcelable {}

@Parcelize data class Sections(
    val from:From,
    val to:To
) : Parcelable {}

@Parcelize data class To(
    val stop_point:StopPointTo
) : Parcelable {}

@Parcelize data class StopPointTo(
    val coord:CoordTo
) : Parcelable {}

@Parcelize data class CoordTo(
    val lat:Double,
    val lon:Double
) : Parcelable {}

@Parcelize data class From(
    val address:AddressFrom
) : Parcelable {}

@Parcelize data class AddressFrom(
    val coord:CoordFrom
) : Parcelable {}

@Parcelize data class CoordFrom(
    val lat:Double,
    val lon:Double
) : Parcelable {}

@Parcelize data class Journey(
    val durations:Durations,
    val sections:List<Sections>
) : Parcelable {}

@Parcelize data class JourneyResult(
    val journeys:List<Journey>
) : Parcelable {}

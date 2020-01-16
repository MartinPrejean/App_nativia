package fr.hetic.app_map_amis.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Journey(@SerializedName("latitudeMarker") val latitudeMarker: String): Parcelable {
}

class JourneyResult {
    var results = listOf<Journey>()
}
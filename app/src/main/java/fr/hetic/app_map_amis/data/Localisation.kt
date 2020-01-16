package fr.hetic.app_map_amis.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize data class Localisation(var id : String, var latitude : Double, var longitude : Double) :
    Parcelable {
}
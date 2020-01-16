package fr.hetic.app_map_amis.network.response

class Durations(
    val taxi:Int,
    val walking:Int,
    val car:Int,
    val ridesharing:Int,
    val bike:Int,
    val total:Int
) {}

/*

class Sections(
    val from:From,
    val to:To
) {}

class To(
    val address:StopPointTo
) {}

class StopPointTo(
    val coord:CoordTo
) {}

class CoordTo(
    val lat:Double,
    val lng:Double
) {}

class From(
    val address:AddressFrom
) {}

class AddressFrom(
    val coord:CoordFrom
) {}

class CoordFrom(
    val lat:Double,
    val lng:Double
) {}


 */
class Journey(
    val durations:Durations
    // val sections:Sections
) {}

data class JourneyResult(
    val journeys:List<Journey>
) {}
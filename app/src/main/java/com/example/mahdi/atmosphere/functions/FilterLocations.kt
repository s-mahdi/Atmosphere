package com.example.mahdi.atmosphere.functions

import com.example.mahdi.atmosphere.models.opencage.Result

fun filterLocations(locations: List<Result>, vararg types: String): List<Result> {
    return locations.filter { types.contains(it.components.type) }
}

fun getLocationName(location: Result): String {
    return when (location.components.type) {
        "city" -> location.components.city?: location.components.town?: "نامعلوم"
        "village" -> location.components.village?: location.components.hamlet?: "نامعلوم"
        else -> "نامعلوم"
    }
}
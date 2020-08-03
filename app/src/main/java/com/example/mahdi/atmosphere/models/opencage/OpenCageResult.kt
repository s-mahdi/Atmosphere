package com.example.mahdi.atmosphere.models.opencage

import com.google.gson.annotations.SerializedName

data class OpenCageResult(
        @SerializedName("results")
        val results: List<Result>
)

data class Result(
        @SerializedName("components")
        val components: Components,

        @SerializedName("confidence")
        val confidence: Int,

        @SerializedName("formatted")
        val formatted: String,

        @SerializedName("geometry")
        val geometry: Geometry
)

data class Components(
        @SerializedName("_category")
        val category: String,

        @SerializedName("_type")
        val type: String,

        @SerializedName("country")
        val country: String,

        @SerializedName("county")
        val county: String?,

        @SerializedName("state")
        val state: String?,

        @SerializedName("town")
        val town: String?,

        @SerializedName("city")
        val city: String?,

        @SerializedName("village")
        val village: String?,

        @SerializedName("hamlet")
        val hamlet: String?
)

data class Geometry(
        @SerializedName("lat")
        val lat: Double,

        @SerializedName("lng")
        val lng: Double
)
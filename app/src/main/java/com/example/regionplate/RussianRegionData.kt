package com.example.regionplate

import com.google.gson.annotations.SerializedName

data class RussianRegionData(
    @SerializedName("country")
    val country: String,

    @SerializedName("regions")
    val regions: List<Regions>
)

data class Regions(
    @SerializedName("regionName")
    val regionName: String,

    @SerializedName("regionNumber")
    val regionNumber: List<String>
)

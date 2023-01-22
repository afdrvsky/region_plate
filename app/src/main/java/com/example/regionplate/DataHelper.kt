package com.example.regionplate

import android.content.Context
import com.google.gson.Gson
import java.io.IOException

class DataHelper {

    fun findRegionByNumber(context: Context, regionNumber: String, fileName: String): String {
        val regionsMap: MutableMap<String, List<String>> = deserializeJson(context, fileName)
        var regionName: String?
        var region = regionsMap.filterValues { it.contains(regionNumber) }.keys
        if (!region.isEmpty()) {
            regionName = region.toString().substring(1, region.toString().length - 1)
        } else {
            regionName = context.getString(R.string.region_not_found)
        }
        return regionName
    }

    fun deserializeJson(context: Context, fileName: String): MutableMap<String, List<String>> {
        val gson = Gson()
        val json = getJsonDataFromAsset(context, fileName)
        val jsonData = gson.fromJson(json, RegionData::class.java)
        val regionsMap: MutableMap<String, List<String>> = mutableMapOf()
        for (i in jsonData.regions.indices) {
            for (j in jsonData.regions.get(i).regionNumber.indices) {
                regionsMap.put(
                    jsonData.regions.get(i).regionName,
                    jsonData.regions.get(i).regionNumber
                )
            }
        }
        return regionsMap
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}
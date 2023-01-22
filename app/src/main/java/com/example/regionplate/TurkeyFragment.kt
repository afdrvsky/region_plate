package com.example.regionplate

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import java.io.IOException

open class TurkeyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_turkey, container, false)
        val justPlate = view.findViewById<ImageView>(R.id.plate_tr)
        val justRegion = view.findViewById<EditText>(R.id.regionNumber)
        val regionPlate = mutableListOf<View>()
        regionPlate.add(justPlate)
        regionPlate.add(justRegion)
        animationOnStart(view)
        justRegion.addTextChangedListener(textWatcher)
        return view
    }

    fun animationOnStart(view: View) {
        val moveLefttoRight = TranslateAnimation(1000f, 0f, 0f, 0f)
        moveLefttoRight.duration = 500
        moveLefttoRight.fillAfter = true
        view.startAnimation(moveLefttoRight)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val justRegion = view?.findViewById<EditText>(R.id.regionNumber)
            val outputText = view?.findViewById<TextView>(R.id.outputRegion)
            outputText?.setText(findRegionByNumber(justRegion?.text.toString()))
            if (justRegion?.text!!.isEmpty() || justRegion?.text.toString().length < 2) {
                outputText?.setText(getString(R.string.type_region))
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    fun findRegionByNumber(regionNumber: String): String {
        var fileName = "trRegion.json"
        val regionsMap: MutableMap<String, List<String>> = deserializeJson(fileName)
        var regionName: String?
        var region = regionsMap.filterValues { it.contains(regionNumber) }.keys
        if (!region.isEmpty()) {
            regionName = region.toString().substring(1, region.toString().length - 1)
        } else {
            regionName = getString(R.string.region_not_found)
        }
        return regionName
    }

    fun deserializeJson(fileName: String): MutableMap<String, List<String>> {
        val gson = Gson()
        val json = getJsonDataFromAsset(requireActivity(), fileName)
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
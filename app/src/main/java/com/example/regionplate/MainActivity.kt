package com.example.regionplate

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val justPlate = findViewById<ImageView>(R.id.plate_rus)
        val justRegion = findViewById<EditText>(R.id.regionNumber)
        val regionPlate = mutableListOf<View>()
        regionPlate.add(justPlate)
        regionPlate.add(justRegion)

        for (i in regionPlate) {
            animationOnStart(i)
        }

        justRegion.addTextChangedListener(textWatcher)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val justRegion = findViewById<EditText>(R.id.regionNumber)
            val outputText = findViewById<TextView>(R.id.outputRegion)
            outputText.setText(findRegionByNumber(justRegion.text.toString()))
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }

    fun animationOnStart(view: View) {
        val moveLefttoRight = TranslateAnimation(-1000f, 0f, 0f, 0f)
        moveLefttoRight.duration = 500
        moveLefttoRight.fillAfter = true
        view.startAnimation(moveLefttoRight)
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

    fun deserializeJson(): MutableMap<String, List<String>> {
        val fileName = """rusRegion.json"""
        val gson = Gson()
        val json = getJsonDataFromAsset(applicationContext, fileName)
        val jsonData = gson.fromJson(json, RussianRegionData::class.java)
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

    fun findRegionByNumber(regionNumber: String) : String? {
        val regionsMap: MutableMap<String, List<String>> = deserializeJson()
        var regionName: String? = null
        for (i in regionsMap.values.indices) {
            var region = regionsMap.filterValues { it.get(i).equals(regionNumber) }.keys
            if (!region.isEmpty()) {
                regionName = region.toString().substring( 1, region.toString().length - 1 )
            }
        }
        return regionName
    }
}
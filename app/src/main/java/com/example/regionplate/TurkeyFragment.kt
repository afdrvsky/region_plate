package com.example.regionplate

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

open class TurkeyFragment : Fragment() {

    val dataHelper = DataHelper()

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
            outputText?.setText(context?.let { dataHelper.findRegionByNumber(it, justRegion?.text.toString(), "trRegion.json") })
            if (justRegion?.text!!.isEmpty() || justRegion?.text.toString().length < 2) {
                outputText?.setText(getString(R.string.type_region))
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    }
}
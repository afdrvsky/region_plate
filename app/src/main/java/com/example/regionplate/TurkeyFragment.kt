package com.example.regionplate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation

class TurkeyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_turkey, container, false)
        animationOnStart(view)
        return view
    }

    fun animationOnStart(view: View) {
        val moveLefttoRight = TranslateAnimation(1000f, 0f, 0f, 0f)
        moveLefttoRight.duration = 500
        moveLefttoRight.fillAfter = true
        view.startAnimation(moveLefttoRight)
    }
}
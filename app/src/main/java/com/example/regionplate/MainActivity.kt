package com.example.regionplate

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)

        findViewById<ImageView>(R.id.menu).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        })

        val navigationView: NavigationView = findViewById(R.id.navigation_menu)
        navigationView.itemIconTintList = null

        val navController: NavController = Navigation.findNavController(this, R.id.nawHostFragment)
        NavigationUI.setupWithNavController(navigationView, navController)
    }
}
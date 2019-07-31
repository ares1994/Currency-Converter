package com.example.android.currencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.currencyconverter.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.toast



/**
 * This is a single activity application that uses the Navigation library. Content is displayed
 * by Fragments.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        //Remove elevation and name from action bar
        title = ""
        supportActionBar!!.elevation = 0f

        //Setting up Drawer
        val navController = this.findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

       //Acessing thanks Item on Drawer and assigning it a custom icon
        val navigationView = findViewById<NavigationView>(R.id.navView)
        val thanks = navigationView.menu.findItem(R.id.thanksPopUp)
        thanks.setOnMenuItemClickListener {
            Snackbar.make(binding.root,"Thanks for using! Hope the experience was great!", Snackbar.LENGTH_LONG).show()
            return@setOnMenuItemClickListener true
        }


    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = this.findNavController(R.id.navHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)



    }




}

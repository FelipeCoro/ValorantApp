package com.sokah.valorantapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.sokah.valorantapp.R
import com.sokah.valorantapp.databinding.ActivityMainBinding
import com.sokah.valorantapp.utils.ConnectionLiveData

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectionLiveData = ConnectionLiveData(this)

        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)

        navController = findNavController(R.id.navHost)
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.agentDetailsFragment || nd.id == R.id.skinDetailFragment || nd.id == R.id.weaponDetailFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
            val appBarConfiguration = AppBarConfiguration
                .Builder(
                    R.id.agentsFragment,
                    R.id.weaponListFragment,
                    R.id.skinsFragment,
                )
                .build()

            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)


        }

    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp()
    }
}
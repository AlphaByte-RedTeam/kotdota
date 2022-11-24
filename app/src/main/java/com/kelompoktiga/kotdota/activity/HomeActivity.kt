package com.kelompoktiga.kotdota.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kelompoktiga.kotdota.R


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        FirebaseAuth.getInstance().signInWithEmailAndPassword("wiyantoeric@gmail.com", "aisdunwad")

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val toolBar = findViewById<MaterialToolbar>(R.id.materialToolbar)
        val navController = findNavController(R.id.fragmentContainerView)
        val toolBarController = findNavController(R.id.fragmentContainerView)

        setSupportActionBar(findViewById(R.id.materialToolbar))

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.heroesFragment, R.id.itemsFragment, R.id.teamsFragment
            )
        )

        val toolBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.app_bar_search
            )
        )

        setupActionBarWithNavController(toolBarController, toolBarConfiguration)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNav.setupWithNavController(navController)
    }
}
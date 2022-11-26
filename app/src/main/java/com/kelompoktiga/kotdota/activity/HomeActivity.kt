package com.kelompoktiga.kotdota.activity

import android.content.Intent
import android.content.res.ColorStateList
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

        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            val loginIntent = Intent(this, LoginActivity::class.java)

            loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(loginIntent)
            finish()
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val toolBar = findViewById<MaterialToolbar>(R.id.materialToolbar)

        val navController = findNavController(R.id.fragmentContainerView)
        val toolBarController = findNavController(R.id.fragmentContainerView)

        setSupportActionBar(findViewById(R.id.materialToolbar))

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.heroesFragment, R.id.savedFragment, R.id.teamsFragment
            )
        )

        bottomNav.itemTextColor = ColorStateList.valueOf(resources.getColor(R.color.ghost_white))

        supportActionBar?.setDisplayShowTitleEnabled(false)
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNav.setupWithNavController(navController)
    }
}
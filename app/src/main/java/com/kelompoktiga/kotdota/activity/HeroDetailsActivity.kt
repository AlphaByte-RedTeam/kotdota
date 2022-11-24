package com.kelompoktiga.kotdota.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.kelompoktiga.kotdota.R
import com.kelompoktiga.kotdota.heroStatsList
import com.squareup.picasso.Picasso


class HeroDetailsActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_details)

        val heroId = intent.getStringExtra("id")
        val hero = heroStatsList[heroId!!.toInt()]

        Picasso.get().load("https://api.opendota.com${hero.img}").into(findViewById<ImageView>(R.id.img_hero))
        findViewById<TextView>(R.id.txt_hero_name).text = hero.localizedName
        findViewById<TextView>(R.id.txt_hero_desc).id = hero.id!!.toInt()
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener(this)
        findViewById<FloatingActionButton>(R.id.fab_like).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_like -> {
                Snackbar.make(v, "Added to favorite", Snackbar.LENGTH_SHORT).show()
            }

            R.id.btn_back -> {
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)
            }
        }
    }
}
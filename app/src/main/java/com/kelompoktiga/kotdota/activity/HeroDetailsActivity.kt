package com.kelompoktiga.kotdota.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.ktx.Firebase
import com.kelompoktiga.kotdota.HeroStatsItem
import com.kelompoktiga.kotdota.R
import com.kelompoktiga.kotdota.data.repository.FirebaseDb
import com.kelompoktiga.kotdota.heroStatsList
import com.squareup.picasso.Picasso


class HeroDetailsActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var hero: HeroStatsItem
    lateinit var fabLike: FloatingActionButton
    lateinit var firebaseDb: FirebaseDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_details)

        firebaseDb = FirebaseDb()
        val heroId = intent.getStringExtra("id")
        hero = heroStatsList[heroId!!.toInt()]

        Picasso.get().load("https://api.opendota.com${hero.img}")
            .into(findViewById<ImageView>(R.id.img_hero))
        findViewById<TextView>(R.id.txt_hero_name).text = hero.localizedName
        findViewById<TextView>(R.id.txt_hero_desc).id = hero.id!!.toInt()
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener(this)

        fabLike = findViewById(R.id.fab_like)
        fabLike.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_like -> {
                var saved: Boolean

                FirebaseDb().getAllSaved {
                    Log.d("saved?", it?.find { it == hero.id.toString() }.toString())

                    if (it?.find { it == hero.id.toString() }.isNullOrEmpty()) {
                        saved = false
                        setUnsavedIcon()
                    } else {
                        saved = true
                        setSavedIcon()
                    }

                    if (saved) {
                        firebaseDb.removeSaved(hero.id.toString())
                        setUnsavedIcon()
                    } else {
                        firebaseDb.addSaved(hero.id.toString())
                        setSavedIcon()
                    }
                }
            }

            R.id.btn_back -> {
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)
            }
        }
    }

    fun setSavedIcon() {
        fabLike.setImageResource(R.drawable.ic_baseline_favorite_24)
    }

    fun setUnsavedIcon() {
        fabLike.setImageResource(R.drawable.ic_baseline_favorite_border_24)
    }
}
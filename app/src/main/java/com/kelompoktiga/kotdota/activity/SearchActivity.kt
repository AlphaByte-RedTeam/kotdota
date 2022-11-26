package com.kelompoktiga.kotdota.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompoktiga.kotdota.HeroGridAdapter
import com.kelompoktiga.kotdota.R
import com.kelompoktiga.kotdota.heroStatsList

class SearchActivity : AppCompatActivity() {
    companion object {
        const val searchName = "search_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val searchName = intent.getStringExtra(searchName)

        if (searchName!!.isNotEmpty()) {
            val searchedHeroes = heroStatsList.filter {
                it.localizedName!!.lowercase() == searchName.lowercase()
            }

            if (searchedHeroes.isEmpty()) {
                findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
                findViewById<LinearLayout>(R.id.empty_search_dialog).visibility = View.VISIBLE
            } else {


                val rvHeroes = findViewById<RecyclerView>(R.id.rv_heroes)
                rvHeroes.setHasFixedSize(true)

                rvHeroes.layoutManager = GridLayoutManager(this, 3)
                val heroGridAdapter = HeroGridAdapter(searchedHeroes.toMutableList())
                rvHeroes.adapter = heroGridAdapter

                heroGridAdapter.setOnItemClickCallback(object :
                    HeroGridAdapter.OnItemClickCallback {
                    override fun onItemClicked(pos: Int) {
                        val heroDetailsIntent =
                            Intent(this@SearchActivity, HeroDetailsActivity::class.java)
                        heroDetailsIntent.putExtra("id", pos.toString())
                        startActivity(heroDetailsIntent)
                    }
                })
                findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
            }
            findViewById<TextView>(R.id.search_name).text = searchName
            findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
                finish()
                Log.d("chch","ye")
            }
        }
    }
}

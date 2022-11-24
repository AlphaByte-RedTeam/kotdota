package com.kelompoktiga.kotdota.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseException
import com.kelompoktiga.kotdota.HeroGridAdapter
import com.kelompoktiga.kotdota.HeroStatsItem
import com.kelompoktiga.kotdota.R
import com.kelompoktiga.kotdota.data.gson.TeamGsonItem
import com.kelompoktiga.kotdota.data.repository.FirebaseDb
import com.kelompoktiga.kotdota.heroStatsList
import com.squareup.picasso.Picasso


class AddTeamActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var team: MutableList<TeamGsonItem>
    lateinit var heroIds: MutableList<String?>
    lateinit var heroList: MutableList<HeroStatsItem>

    lateinit var rvHeroes: RecyclerView
    lateinit var popUpHeroList: CardView
    lateinit var txtTeamTitle: TextView
    lateinit var baseLayout: LinearLayout

    lateinit var selectedHeroIv: ImageView
    lateinit var selectedHeroTv: TextView
    var selectedCardIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_team)

        txtTeamTitle = findViewById(R.id.txt_team_title)
        popUpHeroList = findViewById(R.id.pop_up_hero_list)
        baseLayout = findViewById(R.id.base_layout)

        heroIds = mutableListOf(
            null,
            null,
            null,
            null,
            null,
        )

        findViewById<Button>(R.id.btn_save).setOnClickListener(this)
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener(this)
        findViewById<ImageButton>(R.id.btn_close).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.hero_1).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.hero_2).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.hero_3).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.hero_4).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.hero_5).setOnClickListener(this)

        rvHeroes = findViewById(R.id.rv_heroes)
        val heroGridAdapter = HeroGridAdapter(heroStatsList)

        rvHeroes.setHasFixedSize(true)
        rvHeroes.layoutManager = GridLayoutManager(this, 2)

        rvHeroes.adapter = heroGridAdapter

        heroGridAdapter.setOnItemClickCallback(object : HeroGridAdapter.OnItemClickCallback {
            override fun onItemClicked(pos: Int) {
                val hero = heroStatsList[pos]

                heroIds[selectedCardIndex] = hero.id.toString()

                Picasso.get().load("https://api.opendota.com${heroStatsList[pos].img}")
                    .into(selectedHeroIv)
                selectedHeroTv.text = hero.localizedName

                baseLayout.foreground = resources.getColor(R.color.transparent).toDrawable()
                popUpHeroList.visibility = View.GONE
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                val homeIntent = Intent(v.context, HomeActivity::class.java)
                startActivity(homeIntent)
            }
            R.id.btn_close -> {
                baseLayout.foreground = resources.getColor(R.color.transparent).toDrawable()
                popUpHeroList.visibility = View.GONE
            }
            R.id.btn_save -> {
                val teamTitle = txtTeamTitle.text.toString()

                if (teamTitle.isEmpty()) {
                    AlertDialog.Builder(v.context)
                        .setTitle("Fail to save")
                        .setMessage("Please enter team name")
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(
                            "Dismiss", null
                        )
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                } else if (teamTitle.contains(" ")) {
                    AlertDialog.Builder(v.context)
                        .setTitle("Fail to save")
                        .setMessage("Team title must only be 1 word")
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(
                            "Dismiss", null
                        )
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                }
                try {
                    FirebaseDb().addTeam(
                        TeamGsonItem(
                            teamTitle = teamTitle,
                            heroId1 = heroIds[0],
                            heroId2 = heroIds[1],
                            heroId3 = heroIds[2],
                            heroId4 = heroIds[3],
                            heroId5 = heroIds[4],
                        )
                    )
                } catch (e: FirebaseException) {
                    Log.e("add team:fail", e.toString())
                }

                val homeIntent = Intent(v.context, HomeActivity::class.java)
                startActivity(homeIntent)
            }
            R.id.hero_1 -> {
                selectHeroCard(
                    imgHero = findViewById(R.id.hero_img1),
                    txtHeroName = findViewById(R.id.txt_hero_name1),
                    index = 0
                )
            }
            R.id.hero_2 -> {
                selectHeroCard(
                    imgHero = findViewById(R.id.hero_img2),
                    txtHeroName = findViewById(R.id.txt_hero_name2),
                    index = 1
                )
            }
            R.id.hero_3 -> {
                selectHeroCard(
                    imgHero = findViewById(R.id.hero_img3),
                    txtHeroName = findViewById(R.id.txt_hero_name3),
                    index = 2
                )
            }
            R.id.hero_4 -> {
                selectHeroCard(
                    imgHero = findViewById(R.id.hero_img4),
                    txtHeroName = findViewById(R.id.txt_hero_name4),
                    index = 3
                )
            }
            R.id.hero_5 -> {
                selectHeroCard(
                    imgHero = findViewById(R.id.hero_img5),
                    txtHeroName = findViewById(R.id.txt_hero_name5),
                    index = 4
                )
            }
        }
    }

    private fun selectHeroCard(imgHero: ImageView, txtHeroName: TextView, index: Int) {
        selectedHeroIv = imgHero
        selectedHeroTv = txtHeroName
        selectedCardIndex = index

        baseLayout.foreground = resources.getColor(R.color.dimmed_black).toDrawable()
        popUpHeroList.visibility = View.VISIBLE
    }
}
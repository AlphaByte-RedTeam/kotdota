package com.kelompoktiga.kotdota.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.text.set
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.kelompoktiga.kotdota.*
import com.kelompoktiga.kotdota.data.gson.TeamGsonItem
import com.kelompoktiga.kotdota.data.repository.FirebaseDb
import com.squareup.picasso.Picasso

class TeamDetailsActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var team: TeamGsonItem
    lateinit var heroIds: MutableList<String?>

    lateinit var btnBack: ImageButton
    lateinit var btnEdit: ImageButton
    lateinit var btnSave: ImageButton
    lateinit var btnClose: ImageButton
    lateinit var txtTeamTitle: TextView
    lateinit var editTxtTeamTitle: EditText

    lateinit var rvHeroes: RecyclerView
    lateinit var popUpHeroList: CardView
    lateinit var baseLayout: LinearLayout

    lateinit var selectedHeroIv: ImageView
    lateinit var selectedHeroTv: TextView
    var selectedCardIndex: Int = 0
    lateinit var teamTitle: String
    var index: Int = 0

    val editFrames = listOf(
        R.id.frame_edit1,
        R.id.frame_edit2,
        R.id.frame_edit3,
        R.id.frame_edit4,
        R.id.frame_edit5,
    )

    val editIcons = listOf(
        R.id.btn_edit_hero1,
        R.id.btn_edit_hero2,
        R.id.btn_edit_hero3,
        R.id.btn_edit_hero4,
        R.id.btn_edit_hero5,
    )

    val heroImgIds = listOf(
        R.id.hero_img1,
        R.id.hero_img2,
        R.id.hero_img3,
        R.id.hero_img4,
        R.id.hero_img5,
    )

    val heroNameIds = listOf(
        R.id.txt_hero_name1,
        R.id.txt_hero_name2,
        R.id.txt_hero_name3,
        R.id.txt_hero_name4,
        R.id.txt_hero_name5,
    )

    companion object {
        const val position = "position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)

        index = intent.getStringExtra(position)!!.toInt()
        team = teamList[index]

//        Log.d("team", team.toString())
        buildWidget()
    }

    private fun buildWidget() {
        btnBack = findViewById(R.id.btn_back)
        btnSave = findViewById(R.id.btn_save)
        btnEdit = findViewById(R.id.btn_edit)
        btnClose = findViewById(R.id.btn_close)
        popUpHeroList = findViewById(R.id.pop_up_hero_list)
        baseLayout = findViewById(R.id.base_layout)
        txtTeamTitle = findViewById(R.id.txt_team_title)
        editTxtTeamTitle = findViewById(R.id.edit_text_team_title)

        btnBack.setOnClickListener(this)
        btnSave.setOnClickListener(this)
        btnEdit.setOnClickListener(this)
        btnClose.setOnClickListener(this)
        txtTeamTitle.setOnClickListener(this)

        heroIds = mutableListOf(
            team.heroId1,
            team.heroId2,
            team.heroId3,
            team.heroId4,
            team.heroId5,
        )

        teamTitle = team.teamTitle!!
        txtTeamTitle.text = teamTitle
        editTxtTeamTitle.setText(teamTitle)

//        findViewById<TextView>(R.id.txt_team_desc).text = team.teamDesc

        heroImgIds.forEachIndexed { index, _ ->
            val hero = heroStatsList.find { it.heroId.toString() == heroIds[index].toString() }
            if (hero != null) {
                val heroName = hero?.localizedName
                val heroImg = hero?.img
                findViewById<TextView>(heroNameIds[index]).text = heroName
                Picasso.get().load("https://api.opendota.com$heroImg")
                    .into(findViewById<ImageView>(heroImgIds[index]))
            }
        }

        editFrames.forEach {
            findViewById<CardView>(it).setOnClickListener(this)
        }

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

                removeDimmedBackground()
                popUpHeroList.visibility = View.GONE
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)
            }
            R.id.btn_edit -> {
                enterEditMode()
            }
            R.id.txt_team_title -> {
                enterEditMode()
            }
            R.id.btn_save -> {
                val newTeamTitle = editTxtTeamTitle.text.toString()

                if (newTeamTitle.isEmpty()) {
                    AlertDialog.Builder(v.context)
                        .setTitle("Fail to save")
                        .setMessage("Please enter team name")
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(
                            "Dismiss", null
                        )
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                } else if (newTeamTitle.contains(" ")) {
                    AlertDialog.Builder(v.context)
                        .setTitle("Fail to save")
                        .setMessage("Team title must only be 1 word")
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(
                            "Dismiss", null
                        )
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                } else {
                    try {
                        val newTeam = TeamGsonItem(
                            teamTitle = newTeamTitle,
                            teamKey = team.teamKey,
                            heroId1 = heroIds[0],
                            heroId2 = heroIds[1],
                            heroId3 = heroIds[2],
                            heroId4 = heroIds[3],
                            heroId5 = heroIds[4],
                        )
                        FirebaseDb().updateTeam(team = newTeam, teamKey = team.teamKey!!)

                        teamList[index] = newTeam

                        val teamDetailsIntent =
                            Intent(v.context, TeamDetailsActivity::class.java)
                        teamDetailsIntent.putExtra(position, index.toString())

                        startActivity(teamDetailsIntent)
                        finish()
                    } catch (e: FirebaseException) {
                        Log.e("add team:fail", e.toString())
                        AlertDialog.Builder(v.context)
                            .setTitle("Fail to save")
                            .setMessage("fail to save due to server error")
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(
                                "Dismiss", null
                            )
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                    for (i in 0..4) {
                        findViewById<FrameLayout>(editFrames[i]).visibility = View.GONE
                        findViewById<ImageView>(editIcons[i]).visibility = View.GONE
                    }

                    btnSave.visibility = View.GONE
                    btnEdit.visibility = View.VISIBLE
                    editTxtTeamTitle.visibility = View.GONE
                    txtTeamTitle.visibility = View.VISIBLE
                }
            }
            R.id.frame_edit1 -> {
                selectHeroCard(
                    imgHero = findViewById(R.id.hero_img1),
                    txtHeroName = findViewById(R.id.txt_hero_name1),
                    index = 0
                )
            }
            R.id.frame_edit2 -> {
                selectHeroCard(
                    imgHero = findViewById(R.id.hero_img2),
                    txtHeroName = findViewById(R.id.txt_hero_name2),
                    index = 1
                )
            }
            R.id.frame_edit3 -> {
                selectHeroCard(
                    imgHero = findViewById(R.id.hero_img3),
                    txtHeroName = findViewById(R.id.txt_hero_name3),
                    index = 2
                )
            }
            R.id.frame_edit4 -> {
                selectHeroCard(
                    imgHero = findViewById(R.id.hero_img4),
                    txtHeroName = findViewById(R.id.txt_hero_name4),
                    index = 3
                )
            }
            R.id.frame_edit5 -> {
                selectHeroCard(
                    imgHero = findViewById(R.id.hero_img5),
                    txtHeroName = findViewById(R.id.txt_hero_name5),
                    index = 4
                )
            }
            R.id.btn_close -> {
                removeDimmedBackground()
                popUpHeroList.visibility = View.GONE
            }
        }
    }

    private fun setDimmedBackground() {
        baseLayout.foreground = resources.getColor(R.color.dimmed_black).toDrawable()
    }

    private fun removeDimmedBackground() {
        baseLayout.foreground = resources.getColor(R.color.transparent).toDrawable()
    }

    private fun selectHeroCard(imgHero: ImageView, txtHeroName: TextView, index: Int) {
        selectedHeroIv = imgHero
        selectedHeroTv = txtHeroName
        selectedCardIndex = index

        setDimmedBackground()
        popUpHeroList.visibility = View.VISIBLE
    }

    private fun enterEditMode() {
        for (i in 0..4) {
            findViewById<FrameLayout>(editFrames[i]).visibility = View.VISIBLE
            findViewById<ImageView>(editIcons[i]).visibility = View.VISIBLE
        }

        btnEdit.visibility = View.GONE
        btnSave.visibility = View.VISIBLE
        txtTeamTitle.visibility = View.GONE
        editTxtTeamTitle.visibility = View.VISIBLE
    }
}
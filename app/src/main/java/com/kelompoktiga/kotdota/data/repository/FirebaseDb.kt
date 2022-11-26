package com.kelompoktiga.kotdota.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kelompoktiga.kotdota.data.gson.TeamGsonItem
import java.lang.reflect.Type

class FirebaseDb {
    private companion object {
        private val database =
            Firebase.database("https://kotdota-678f3-default-rtdb.asia-southeast1.firebasedatabase.app/")
        private val uid = Firebase.auth.currentUser?.uid
    }

    private fun getInstance(): FirebaseDatabase {
        return database
    }

    private fun getTeamRef(): DatabaseReference {
        return getInstance().getReference("team/$uid")
    }

    private fun getSavedRef(): DatabaseReference {
        return getInstance().getReference("saved/$uid")
    }

    fun awaitGetAllTeams(callback: (List<TeamGsonItem>?) -> Unit) {
        val teamList: MutableList<TeamGsonItem>? = mutableListOf()

        getTeamRef().get().addOnSuccessListener {
            val teams = it.children.map {
                val listType: Type = object : TypeToken<TeamGsonItem?>() {}.type
                return@map Gson().fromJson<TeamGsonItem>(it.value.toString(), listType)
            }
            teamList!!.addAll(teams)
            callback(teamList)
        }
    }

    fun getAllSaved(callback: (List<String>?) -> Unit) {
        getSavedRef().child("hero_id").get().addOnSuccessListener {
            val heroIdList = it.value.toString().split(",").toTypedArray().filter {
                it.isNotEmpty()
            }
            callback(heroIdList)
        }
    }

    fun addSaved(heroId: String) {
        getAllSaved { savedIds ->
//            check if there is no current adding id
            if (savedIds!!.find { it.equals(heroId) }.isNullOrEmpty()) {
                val newHeroIdList = savedIds.filter {
                    it != "null"
                }.toMutableList()

                newHeroIdList.add(heroId)

                val newHeroIdListString = newHeroIdList.joinToString(",")

                getSavedRef().child("hero_id").setValue(newHeroIdListString)
            }
        }
    }

//    fun updateSaved(heroIds: List<String>) {
//        val newHeroIdListString = heroIds.joinToString(",")
//        getSavedRef().child("hero_id").setValue(newHeroIdListString)
//    }

    fun removeSaved(heroId: String) {
        getAllSaved {
            if (!it!!.find { it.equals(heroId) }.isNullOrEmpty()) {
                val newHeroList = it.toMutableList()

                newHeroList.remove(heroId)
                val newHeroIdListString = newHeroList.joinToString(",")

                getSavedRef().child("hero_id").setValue(newHeroIdListString)
            }
        }
    }

    fun addTeam(team: TeamGsonItem) {
        val teamKey = getTeamRef().push().key
        getTeamRef().child(teamKey!!).setValue(
            mapOf(
                "team_key" to teamKey.toString(),
                "team_title" to team.teamTitle.toString(),
//                "team_desc" to team.teamDesc.toString(),
                "hero_id1" to team.heroId1.toString(),
                "hero_id2" to team.heroId2.toString(),
                "hero_id3" to team.heroId3.toString(),
                "hero_id4" to team.heroId4.toString(),
                "hero_id5" to team.heroId5.toString(),
            )
        )
    }

    fun updateTeam(team: TeamGsonItem, teamKey: String) {
        getTeamRef().child(teamKey).updateChildren(
            mapOf(
                "team_title" to team.teamTitle.toString(),
//                "team_desc" to team.teamDesc.toString(),
                "hero_id1" to team.heroId1.toString(),
                "hero_id2" to team.heroId2.toString(),
                "hero_id3" to team.heroId3.toString(),
                "hero_id4" to team.heroId4.toString(),
                "hero_id5" to team.heroId5.toString(),
            )
        )
    }

    fun removeTeam(teamKey: String) {
        getTeamRef().child(teamKey).removeValue()
    }
}
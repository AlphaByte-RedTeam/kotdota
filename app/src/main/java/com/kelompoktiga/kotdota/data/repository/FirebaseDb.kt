package com.kelompoktiga.kotdota.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

     fun getInstance(): FirebaseDatabase {
        return database
    }

    private fun getTeamRef(): DatabaseReference {
        return getInstance().getReference("team/$uid")
    }

    fun getSavedRef(): DatabaseReference {
        return getInstance().getReference("saved/$uid")
    }

    fun getAllTeam(): List<TeamGsonItem>? {
        var gson: List<TeamGsonItem>

       getTeamRef().get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            val achi = it.value.toString()

            val listType: Type = object : TypeToken<ArrayList<TeamGsonItem?>?>() {}.type
            gson = Gson().fromJson(achi, listType)
        }.addOnFailureListener(
            return null
        )

        return gson
    }

    fun addTeam(team: TeamGsonItem) {
        val teamKey = getTeamRef().push().key
        getTeamRef().child(teamKey!!).setValue(
            mapOf(
                "team_key" to teamKey.toString(),
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
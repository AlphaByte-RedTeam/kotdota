package com.kelompoktiga.kotdota.data.gson

import com.google.gson.annotations.SerializedName

data class TeamGson(

    @field:SerializedName("TeamGson")
    val teamGson: List<TeamGsonItem?>? = null
)

data class TeamGsonItem(

    @field:SerializedName("team_key")
    val teamKey: String? = null,

    @field:SerializedName("team_title")
    val teamTitle: String? = null,

//    @field:SerializedName("team_desc")
//    val teamDesc: String? = null,

    @field:SerializedName("hero_id1")
    val heroId1: String? = null,

    @field:SerializedName("hero_id2")
    val heroId2: String? = null,

    @field:SerializedName("hero_id3")
    val heroId3: String? = null,

    @field:SerializedName("hero_id4")
    val heroId4: String? = null,

    @field:SerializedName("hero_id5")
    val heroId5: String? = null
)

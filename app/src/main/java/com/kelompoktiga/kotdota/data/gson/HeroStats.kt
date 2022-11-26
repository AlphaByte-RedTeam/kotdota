package com.kelompoktiga.kotdota

import com.google.gson.annotations.SerializedName

data class HeroStats(

	@field:SerializedName("HeroStats")
	val heroStats: List<HeroStatsItem?>? = null
)

data class HeroStatsItem(

	@field:SerializedName("primary_attr")
	val primaryAttr: String? = null,

	@field:SerializedName("attack_range")
	val attackRange: Int? = null,

	@field:SerializedName("attack_type")
	val attackType: String? = null,

	@field:SerializedName("5_pick")
	val jsonMember5Pick: Int? = null,

	@field:SerializedName("pro_pick")
	val proPick: Int? = null,

	@field:SerializedName("1_win")
	val jsonMember1Win: Int? = null,

	@field:SerializedName("base_health")
	val baseHealth: Int? = null,

	@field:SerializedName("legs")
	val legs: Int? = null,

	@field:SerializedName("8_win")
	val jsonMember8Win: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("6_pick")
	val jsonMember6Pick: Int? = null,

	@field:SerializedName("str_gain")
	val strGain: Any? = null,

	@field:SerializedName("2_win")
	val jsonMember2Win: Int? = null,

	@field:SerializedName("attack_rate")
	val attackRate: Any? = null,

	@field:SerializedName("base_str")
	val baseStr: Int? = null,

	@field:SerializedName("3_win")
	val jsonMember3Win: Int? = null,

	@field:SerializedName("agi_gain")
	val agiGain: Any? = null,

	@field:SerializedName("8_pick")
	val jsonMember8Pick: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("turbo_picks")
	val turboPicks: Int? = null,

	@field:SerializedName("projectile_speed")
	val projectileSpeed: Int? = null,

	@field:SerializedName("null_win")
	val nullWin: Int? = null,

	@field:SerializedName("pro_win")
	val proWin: Int? = null,

	@field:SerializedName("img")
	val img: String? = null,

	@field:SerializedName("cm_enabled")
	val cmEnabled: Boolean? = null,

	@field:SerializedName("5_win")
	val jsonMember5Win: Int? = null,

	@field:SerializedName("roles")
	val roles: List<String?>? = null,

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("turbo_wins")
	val turboWins: Int? = null,

	@field:SerializedName("base_mana")
	val baseMana: Int? = null,

	@field:SerializedName("localized_name")
	val localizedName: String? = null,

	@field:SerializedName("2_pick")
	val jsonMember2Pick: Int? = null,

	@field:SerializedName("4_win")
	val jsonMember4Win: Int? = null,

	@field:SerializedName("hero_id")
	val heroId: Int? = null,

	@field:SerializedName("3_pick")
	val jsonMember3Pick: Int? = null,

	@field:SerializedName("7_pick")
	val jsonMember7Pick: Int? = null,

	@field:SerializedName("7_win")
	val jsonMember7Win: Int? = null,

	@field:SerializedName("base_armor")
	val baseArmor: Double? = null,

	@field:SerializedName("1_pick")
	val jsonMember1Pick: Int? = null,

	@field:SerializedName("4_pick")
	val jsonMember4Pick: Int? = null,

	@field:SerializedName("base_mana_regen")
	val baseManaRegen: Double? = null,

	@field:SerializedName("base_attack_max")
	val baseAttackMax: Int? = null,

	@field:SerializedName("base_int")
	val baseInt: Int? = null,

	@field:SerializedName("int_gain")
	val intGain: Any? = null,

	@field:SerializedName("move_speed")
	val moveSpeed: Int? = null,

	@field:SerializedName("turn_rate")
	val turnRate: Any? = null,

	@field:SerializedName("null_pick")
	val nullPick: Int? = null,

	@field:SerializedName("pro_ban")
	val proBan: Int? = null,

	@field:SerializedName("6_win")
	val jsonMember6Win: Int? = null,

	@field:SerializedName("base_attack_min")
	val baseAttackMin: Double? = null,

	@field:SerializedName("base_agi")
	val baseAgi: Double? = null,

	@field:SerializedName("base_health_regen")
	val baseHealthRegen: Double? = null,

	@field:SerializedName("base_mr")
	val baseMr: Double? = null
)

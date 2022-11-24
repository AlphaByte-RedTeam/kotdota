package com.kelompoktiga.kotdota

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelompoktiga.kotdota.data.gson.TeamGsonItem
import com.squareup.picasso.Picasso

class TeamGridAdapter(private val teamList: List<TeamGsonItem>) :
    RecyclerView.Adapter<TeamGridAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.team_list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        TODO: konekin ini
        val heroImg = heroStatsList.find { it.heroId.toString() == teamList[position].heroId1 }?.img
        Picasso.get().load("https://api.opendota.com$heroImg").into(holder.imgHero)
        holder.txtTeamTitle.text = teamList[position].teamTitle
//        holder.txtTeamDesc.text = teamList[position].teamDesc
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(position) }
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgHero: ImageView = itemView.findViewById(R.id.img_hero)
        var txtTeamTitle: TextView = itemView.findViewById(R.id.txt_team_title)
//        var txtTeamDesc: TextView = itemView.findViewById(R.id.txt_team_desc)
    }

    interface OnItemClickCallback {
        fun onItemClicked(position: Int)
    }
}
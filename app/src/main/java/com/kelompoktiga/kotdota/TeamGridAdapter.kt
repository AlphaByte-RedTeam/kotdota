package com.kelompoktiga.kotdota

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.kelompoktiga.kotdota.data.gson.TeamGsonItem
import com.squareup.picasso.Picasso


class TeamGridAdapter(private val view: View, private val teamList: List<TeamGsonItem>) :
    RecyclerView.Adapter<TeamGridAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onDeleteClickCallback: OnDeleteClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnDeleteCickCallback(onDeleteClickCallback: OnDeleteClickCallback) {
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.team_list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val team = teamList[position]
        val heroImg = heroStatsList.find { it.heroId.toString() == team.heroId1 }?.img

        Picasso.get().load("https://api.opendota.com$heroImg").into(holder.imgHero)

        holder.txtTeamTitle.text = teamList[position].teamTitle
//        holder.txtTeamDesc.text = teamList[position].teamDesc
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(position) }

//        set popupmenu click callback
        holder.popupMenu.inflate(R.menu.team_popup_menu)
        holder.menu.setOnClickListener {
            holder.popupMenu.show()
        }

//        set popupmenu item click callback
        holder.popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    onDeleteClickCallback.onDeleteClicked(position)
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgHero: ImageView = itemView.findViewById(R.id.img_hero)
        var txtTeamTitle: TextView = itemView.findViewById(R.id.txt_team_title)
        var menu: ImageButton = itemView.findViewById(R.id.popup_menu)
        val popupMenu = PopupMenu(itemView.context, itemView, Gravity.END, androidx.appcompat.R.attr.actionOverflowMenuStyle, 0)
//        var txtTeamDesc: TextView = itemView.findViewById(R.id.txt_team_desc)
    }

    interface OnItemClickCallback {
        fun onItemClicked(position: Int)
    }

    interface OnDeleteClickCallback {
        fun onDeleteClicked(position: Int)
    }

}

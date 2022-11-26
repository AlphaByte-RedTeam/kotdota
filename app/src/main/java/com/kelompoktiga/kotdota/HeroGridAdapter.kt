package com.kelompoktiga.kotdota

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class HeroGridAdapter(private val heroList: MutableList<HeroStatsItem>) :
    RecyclerView.Adapter<HeroGridAdapter.GridViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.hero_grid_item, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val hero = heroList[position]
        val savedPos = heroStatsList.indexOf(
            heroStatsList.find {
                it.heroId == hero.heroId
            }
        )

        Picasso.get().load("https://api.opendota.com${hero.img}")
            .into(holder.heroImg)
        holder.heroName.text = hero.localizedName
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(savedPos) }}

    override fun getItemCount(): Int {
        return heroList.size
    }

    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var heroImg: ImageView = itemView.findViewById(R.id.img_hero)
        var heroName: TextView = itemView.findViewById(R.id.txt_hero_name)
    }

    interface OnItemClickCallback {
        fun onItemClicked(pos: Int)
    }

}
package com.kelompoktiga.kotdota.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kelompoktiga.kotdota.R
import com.kelompoktiga.kotdota.activity.TeamDetailsActivity
import com.kelompoktiga.kotdota.TeamGridAdapter
import com.kelompoktiga.kotdota.activity.AddTeamActivity
import com.kelompoktiga.kotdota.data.gson.TeamGsonItem
import com.kelompoktiga.kotdota.data.repository.FirebaseDb
import com.kelompoktiga.kotdota.teamList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TeamsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeamsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_teams, container, false)
        val firebaseDb = FirebaseDb()

        firebaseDb.awaitGetAllTeams {
            if (teamList.isEmpty()) it?.let { it1 -> teamList.addAll(it1) }

            buildWidget(view, it)
        }

        return view
    }

    private fun buildWidget(view: View, heroes: List<TeamGsonItem>?) {
        view.findViewById<FloatingActionButton>(R.id.fab_add).setOnClickListener{
            val addTeamIntent = Intent(it.context, AddTeamActivity::class.java)
            startActivity(addTeamIntent)
        }

        val rvHeroes = view.findViewById<RecyclerView>(R.id.rv_heroes)
        rvHeroes.setHasFixedSize(true)

        rvHeroes.layoutManager = LinearLayoutManager(parentFragment?.context)
        val teamGridAdapter = TeamGridAdapter(heroes!!)
        rvHeroes.adapter = teamGridAdapter

        teamGridAdapter.setOnItemClickCallback(object : TeamGridAdapter.OnItemClickCallback {
            override fun onItemClicked(position: Int) {
                val teamDetailsIntent =
                    Intent(parentFragment!!.context, TeamDetailsActivity::class.java)
                teamDetailsIntent.putExtra(TeamDetailsActivity.position, position.toString())
                startActivity(teamDetailsIntent)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TeamsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
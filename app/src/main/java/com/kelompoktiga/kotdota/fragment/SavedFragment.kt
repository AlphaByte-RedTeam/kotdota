package com.kelompoktiga.kotdota.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kelompoktiga.kotdota.*
import com.kelompoktiga.kotdota.activity.HeroDetailsActivity
import com.kelompoktiga.kotdota.data.repository.FirebaseDb


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedFragment : Fragment() {
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
        val view: View = inflater.inflate(R.layout.fragment_saved, container, false)

        FirebaseDb().getAllSaved { savedHeroes ->
            if (savedHeroes.isNullOrEmpty()) {
                view.findViewById<LinearLayout>(R.id.empty_saved_dialog).visibility = View.VISIBLE
            } else {
                val savedHeroList = savedHeroes.map { savedId ->
                    heroStatsList.find {
                        it.id == savedId.toInt()
                    }
                }

                val rvHeroes = view.findViewById<RecyclerView>(R.id.rv_heroes)
                rvHeroes.setHasFixedSize(true)

                rvHeroes.layoutManager = GridLayoutManager(parentFragment?.context, 3)
                val heroGridAdapter = SavedGridAdapter(savedHeroList as MutableList<HeroStatsItem>)

                rvHeroes.adapter = heroGridAdapter
                heroGridAdapter.setOnItemClickCallback(object :
                    SavedGridAdapter.OnItemClickCallback {
                    override fun onItemClicked(pos: Int) {
                        val heroDetailsIntent =
                            Intent(parentFragment!!.context, HeroDetailsActivity::class.java)
                        heroDetailsIntent.putExtra("id", pos.toString())
                        startActivity(heroDetailsIntent)
                    }
                })
            }
            view.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE
        }
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ItemsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
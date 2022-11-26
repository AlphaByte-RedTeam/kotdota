package com.kelompoktiga.kotdota.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.kelompoktiga.kotdota.*
import com.kelompoktiga.kotdota.activity.HeroDetailsActivity
import com.kelompoktiga.kotdota.activity.HomeActivity
import com.kelompoktiga.kotdota.activity.LoginActivity
import com.kelompoktiga.kotdota.activity.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HeroesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HeroesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var isHeroesFetched: Boolean = false
    private lateinit var editSearch: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_heroes, container, false)
        editSearch = view.findViewById(R.id.edit_search)

        editSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchHero(view)
                return@OnEditorActionListener true
            }
            false
        })

        HeroApi().getHeroStatsService().getHeroStats()
            .enqueue(object : Callback<List<HeroStatsItem>> {
                override fun onResponse(
                    call: Call<List<HeroStatsItem>>,
                    response: Response<List<HeroStatsItem>>
                ) {
                    val heroStats = response.body()
                    if (heroStatsList.isEmpty()) {
                        heroStatsList.addAll(heroStats!!)
                    }
                    buildWidget(view)
                    isHeroesFetched = true
                }

                override fun onFailure(call: Call<List<HeroStatsItem>>, t: Throwable) {
                    Log.e("fetch hero:fail", t.toString())
                }
            })

        view.findViewById<ImageButton>(R.id.btn_popup).setOnClickListener { it ->
            val popup = PopupMenu(view.context, it)
            popup.inflate(R.menu.home_popup_menu)
            popup.setForceShowIcon(true)
            popup.show()

            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item?.itemId) {
                    R.id.logout -> {
                        FirebaseAuth.getInstance().signOut()
                        val loginIntent = Intent(requireParentFragment().context, LoginActivity::class.java)

                        loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(loginIntent)
                        requireParentFragment().requireActivity().finish()
                    }
                }
                true
            }
        }

        return view;
    }

    private fun buildWidget(view: View) {
        val rvHeroes = view.findViewById<RecyclerView>(R.id.rv_heroes)
        rvHeroes.setHasFixedSize(true)

        rvHeroes.layoutManager = GridLayoutManager(parentFragment?.context, 3)
        val heroGridAdapter = HeroGridAdapter(heroStatsList)
        rvHeroes.adapter = heroGridAdapter

        heroGridAdapter.setOnItemClickCallback(object : HeroGridAdapter.OnItemClickCallback {
            override fun onItemClicked(pos: Int) {
                val heroDetailsIntent =
                    Intent(parentFragment!!.context, HeroDetailsActivity::class.java)
                heroDetailsIntent.putExtra("id", pos.toString())
                startActivity(heroDetailsIntent)
            }
        })
    }

    private fun searchHero(view: View) {
        val searchName = editSearch.text.toString()

        if (searchName.isNotEmpty()) {
            val searchIntent = Intent(view.context, SearchActivity::class.java)
            searchIntent.putExtra(SearchActivity.searchName, searchName)
            startActivity(searchIntent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HeroesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HeroesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
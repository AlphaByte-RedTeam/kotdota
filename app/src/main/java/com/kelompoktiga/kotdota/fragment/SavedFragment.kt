package com.kelompoktiga.kotdota.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.kelompoktiga.kotdota.R
import com.kelompoktiga.kotdota.data.gson.TeamGsonItem
import com.kelompoktiga.kotdota.data.repository.FirebaseDb
import java.lang.reflect.Type


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
        val t = view.findViewById<TextView>(R.id.txt_test)
        val uid = Firebase.auth.currentUser?.uid
        val f = FirebaseDb()

        view.findViewById<Button>(R.id.btn_test).setOnClickListener {
//            f.getSavedRef().child("heroId").get().addOnSuccessListener {
//                val heroIdList = it.value.toString().split(",").toTypedArray().filter {
//                    it.isNotEmpty()
//                }
//
//                val newHeroId = "100"
//
//                if (heroIdList.find { it.equals(newHeroId) }.isNullOrEmpty()) {
//                    val newHeroIdList = heroIdList.toMutableList()
//                    newHeroIdList.add(newHeroId)
//                    val newHeroIdListString = newHeroIdList.joinToString(",")
//                    Log.d("adiw", newHeroIdListString)
//                    f.getSavedRef().child("heroId").setValue(newHeroIdListString)
//                } else {
//                    Log.d("adiw", "ad data")
//                }
//            }
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
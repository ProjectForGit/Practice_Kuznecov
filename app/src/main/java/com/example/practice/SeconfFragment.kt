package com.example.practice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_seconf.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SeconfFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SeconfFragment : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_seconf, container, false)
        val cityListView = root.findViewById<ListView>(R.id.listView)
        cityListView.adapter = ArrayAdapter<String>(requireContext(), R.layout.text_view, resources.getStringArray(R.array.cities))

        cityListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val contains = sharedPref.contains("city")
            val city = parent.getItemAtPosition(position) as String
            Toast.makeText(context, "Город: $city", Toast.LENGTH_SHORT).show()
            with(sharedPref.edit()) { putString("city", city)

                apply()

            }
            if (!contains)
                parentFragmentManager.beginTransaction().replace(R.id.fragment, FirstFragment()).commit()}

        cityListView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.blure))
        return root
    }
}
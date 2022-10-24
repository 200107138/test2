package com.example.test

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.example.test.databinding.HomeLayoutBinding
import com.example.test.databinding.ReactionLayoutBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [home_layout.newInstance] factory method to
 * create an instance of this fragment.
 */
class Homelayout : Fragment(R.layout.home_layout) {
    // TODO: Rename and change types of parameters

    private lateinit var binding: HomeLayoutBinding

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        var recyclerView: Button = view.findViewById(R.id.startbutton)
        recyclerView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_home_layout_to_reaction_layout)
        }
    }

}
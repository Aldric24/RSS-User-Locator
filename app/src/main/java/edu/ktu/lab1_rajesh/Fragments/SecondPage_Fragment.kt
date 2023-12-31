package edu.ktu.lab1_rajesh.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import edu.ktu.lab1_rajesh.databinding.FragmentSecondPageBinding
import edu.ktu.lab1_rajesh.viewModels.SecondPageViewModel


class SecondPage_Fragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSecondPageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSecondPageBinding.inflate(layoutInflater, container, false)


        val viewModel: SecondPageViewModel by viewModels()
        viewModel.loaddata(requireActivity())
        viewModel.dataLoadedLiveData.observe(viewLifecycleOwner) { dataLoaded ->
            if (dataLoaded) {
                view?.let { activity?.let { it1 -> viewModel.addrss(it, binding, it1) }}
                //Toast.makeText(activity, "loaddata", Toast.LENGTH_SHORT).show()
            }
        }



        return binding.root

        }


    }

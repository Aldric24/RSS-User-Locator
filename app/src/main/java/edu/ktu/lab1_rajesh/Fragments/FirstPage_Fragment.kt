package edu.ktu.lab1_rajesh.Fragments

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import edu.ktu.lab1_rajesh.API_Retro.ApI
import edu.ktu.lab1_rajesh.app_models.Strength
import edu.ktu.lab1_rajesh.R
import edu.ktu.lab1_rajesh.SQLConnector.SqlConnection
import edu.ktu.lab1_rajesh.viewModels.FirstPageViewModel
import edu.ktu.lab1_rajesh.app_models.Matavimas
import edu.ktu.lab1_rajesh.databinding.FragmentFirstPageBinding
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class FirstPage_Fragment : Fragment() {
    private lateinit var binding: FragmentFirstPageBinding
    private lateinit var recyclerViewM: RecyclerView
    private lateinit var recyclerViewX: RecyclerView
    private lateinit var recyclerViewY: RecyclerView
    private lateinit var matavimai: MutableList<Matavimas>
    private val mapset = mutableSetOf<Pair<Int, Int>>()
    private lateinit var rSSlist: MutableList<Strength>

    var s1: Int = 0
    var s2: Int = 0
    var s3: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFirstPageBinding.inflate(layoutInflater, container, false)
        val viewModel: FirstPageViewModel by viewModels()


        recyclerViewM = binding.recyclerView
        recyclerViewX = binding.xAxisrecyclerView
        recyclerViewY = binding.yAxisrecyclerView
        viewModel.ialize(resources,requireActivity())
        viewModel.userloc(recyclerViewM, recyclerViewX, recyclerViewY)
        return binding.root

    }







}
package edu.ktu.lab1_rajesh.viewModels

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.ktu.lab1_rajesh.app_models.GridRss
import edu.ktu.lab1_rajesh.databinding.FragmentSecondPageBinding
import java.util.regex.Pattern

class SecondPageViewModel: ViewModel() {

    private var read=MutableLiveData<List<GridRss>>()
    private var navController: NavController?=null
    val dataLoadedLiveData = MutableLiveData<Boolean>()
    fun loaddata(activity: Activity)
    {
        read.value= mutableListOf()
        read.value=readFromSharedPreference(activity)
        dataLoadedLiveData.value = true


    }

    fun addrss(view: View,binding: FragmentSecondPageBinding,activity: Activity)
    {

        navController=Navigation.findNavController(view)
        binding.add.setOnClickListener {
            val isValid = validateInput(binding)
            if (isValid) {
                val s1 = binding.s1.text.toString().toInt()
                val s2 = binding.s2.text.toString().toInt()
                val s3 = binding.s3.text.toString().toInt()
                val macAddress = binding.macAddress.text.toString()
                val newRSS = GridRss(macAddress, s1, s2, s3)
                val existingRSSList = readFromSharedPreference(activity)
                val updateRSSList = existingRSSList.toMutableList()
                updateRSSList.add(newRSS)
                saveToSharedPreference(updateRSSList,activity)
                clearInputs(binding)
            }
        }

    }

     fun validateInput(binding: FragmentSecondPageBinding): Boolean {

        var isValid = true

        if (binding.macAddress.text.isNullOrEmpty()) {
            binding.macAddress.error = "Please enter a valid MAC address"
            isValid = false
        }

        if (binding.s1.text.isNullOrEmpty()) {
            binding.s1.error = "Please enter a valid RSS value"
            isValid = false
        }

        if (binding.s2.text.isNullOrEmpty()) {
            binding.s2.error = "Please enter a valid RSS value"
            isValid = false
        }

        if (binding.s3.text.isNullOrEmpty()) {
            binding.s3.error = "Please enter a valid RSS value"
            isValid = false
        }

        if (!isMacAddressValid(binding.macAddress.text.toString())){
            binding.macAddress.error = "Please enter a valid MAC address"
            isValid = false

        }

        return isValid

    }


    private fun clearInputs(binding: FragmentSecondPageBinding) {
        binding.macAddress.text?.clear()
        binding.s1.text?.clear()
        binding.s2.text?.clear()
        binding.s3.text?.clear()

    }
    private fun readFromSharedPreference(activity: Activity): List<GridRss> {
        val sharedPreference = activity.getSharedPreferences("sharedPreference", 0)
        val json = sharedPreference?.getString("rssList", null) ?: return emptyList()

        val gson = Gson()
        val type = object : TypeToken<List<GridRss>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    private fun saveToSharedPreference(rssList: List<GridRss>,activity: Activity) {
        val gson = Gson()
        val json = gson.toJson(rssList)
        val sharedPreference = activity.getSharedPreferences("sharedPreference", 0)
        val editor = sharedPreference?.edit()
        editor?.putString("rssList", json)
        editor?.apply()
    }


    fun isMacAddressValid(macAddress: String): Boolean {
        val pattern = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")
        val matcher = pattern.matcher(macAddress)
        return matcher.matches()
    }
}

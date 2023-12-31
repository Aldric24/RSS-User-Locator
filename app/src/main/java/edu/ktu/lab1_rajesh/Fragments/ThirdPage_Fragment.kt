package edu.ktu.lab1_rajesh.Fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.ktu.lab1_rajesh.Adapters.RSSCard_Adapter
import edu.ktu.lab1_rajesh.R
import edu.ktu.lab1_rajesh.app_models.GridRss
import edu.ktu.lab1_rajesh.databinding.FragmentThirdPageBinding

class ThirdPage_Fragment : Fragment() {
private lateinit var binding: FragmentThirdPageBinding
private lateinit var rsslist: MutableList<GridRss>
private lateinit var adapter: RSSCard_Adapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
          binding= FragmentThirdPageBinding.inflate(layoutInflater,container,false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializefun()
        rsslist.addAll(readRSSListFromSharedPreference())


        adapter.notifyDataSetChanged()
    }
    private fun initializefun() {
        binding.thirdpage.layoutManager = LinearLayoutManager(context)
        rsslist = mutableListOf()
        adapter = RSSCard_Adapter(rsslist, this::deleteRSS,this::showEditDialog)
        binding.thirdpage.adapter = adapter
        adapter.onItemClicked = this::showRSSCELL

    }

    private fun showRSSCELL(gridRss: GridRss)
    {



        val s1= gridRss.s1
        val s2= gridRss.s2
        val s3= gridRss.s3
        saveValuesToSharedPreference(s1,s2,s3)
        Log.d("s1",s1.toString())
        Log.d("s2",s2.toString())
        Log.d("s3",s3.toString())
        findNavController().navigate(R.id.action_thirdPage_Fragment_to_firstPage_Fragment)

    }
    private fun showEditDialog(rss: GridRss) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.edit_screen, null)
        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val dialog = alertDialogBuilder.show()

        dialogView.findViewById<EditText>(R.id.editMacAddress).setText(rss.macAddress)
        dialogView.findViewById<EditText>(R.id.editS1).setText(rss.s1.toString())
        dialogView.findViewById<EditText>(R.id.editS2).setText(rss.s2.toString())
        dialogView.findViewById<EditText>(R.id.editS3).setText(rss.s3.toString())



        dialogView.findViewById<Button>(R.id.buttonUpdate).setOnClickListener {
            // Validate and update RSS
            val updatedRSS = GridRss(
                dialogView.findViewById<EditText>(R.id.editMacAddress).text.toString(),
                dialogView.findViewById<EditText>(R.id.editS1).text.toString().toInt(),
                dialogView.findViewById<EditText>(R.id.editS2).text.toString().toInt(),
                dialogView.findViewById<EditText>(R.id.editS3).text.toString().toInt()
            )
            updateRSSList(updatedRSS)
            dialog.dismiss()
        }
    }
    private fun readRSSListFromSharedPreference(): List<GridRss> {
        val sharedPreference = activity?.getSharedPreferences("sharedPreference", 0)
        val json = sharedPreference?.getString("rssList", null)
        if (json.isNullOrEmpty()) return emptyList()

        val gson = Gson()
        val type = object : TypeToken<List<GridRss>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
    private fun deleteRSS(position: Int) {
        val deletedMac = rsslist[position].macAddress
        rsslist.removeAt(position)
        adapter.notifyItemRemoved(position)
        saveRSSListToSharedPreference(rsslist)

        val deletedMacs = readDeletedMacs().toMutableSet()
        deletedMacs.add(deletedMac)
        saveDeletedMacs(deletedMacs)
    }
    private fun updateRSSList(updatedRSS: GridRss) {
        val index = rsslist.indexOfFirst { it.macAddress == updatedRSS.macAddress }
        if (index != -1) {
            rsslist[index] = updatedRSS
            adapter.notifyItemChanged(index)
            saveRSSListToSharedPreference(rsslist)
        }
    }
    private fun saveDeletedMacs(deletedMacs: Set<String>) {
        val gson = Gson()
        val json = gson.toJson(deletedMacs)
        val sharedPreference = activity?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor?.putString("deletedMacs", json)
        editor?.apply()
    }

    private fun readDeletedMacs(): Set<String> {
        val sharedPreference = activity?.getSharedPreferences("sharedPreference", 0)
        val json = sharedPreference?.getString("deletedMacs", null) ?: return emptySet()

        val gson = Gson()
        val type = object : TypeToken<Set<String>>() {}.type
        return gson.fromJson(json, type) ?: emptySet()
    }
    private fun saveRSSListToSharedPreference(rssList: List<GridRss>) {
        val gson = Gson()
        val json = gson.toJson(rssList)
        val sharedPreference = activity?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor?.putString("rssList", json)
        editor?.apply()
    }
    private fun saveValuesToSharedPreference(value1: Int, value2: Int,value3: Int) {
        val gson = Gson()
        val sharedPreference = activity?.getSharedPreferences("SelectedRSS", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()

        // Serialize each value separately
        val jsonValue1 = gson.toJson(value1)
        val jsonValue2 = gson.toJson(value2)
        val jsonValue3 = gson.toJson(value3)

        // Save the serialized values to SharedPreferences
        editor?.putString("value1", jsonValue1)
        editor?.putString("value2", jsonValue2)
        editor?.putString("value3", jsonValue3)

        editor?.apply()
    }


}
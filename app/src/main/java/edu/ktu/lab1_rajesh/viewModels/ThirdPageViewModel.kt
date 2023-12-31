package edu.ktu.lab1_rajesh.viewModels

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.ktu.lab1_rajesh.Adapters.RSSCard_Adapter
import edu.ktu.lab1_rajesh.R
import edu.ktu.lab1_rajesh.app_models.GridRss
import edu.ktu.lab1_rajesh.databinding.FragmentSecondPageBinding
import edu.ktu.lab1_rajesh.databinding.FragmentThirdPageBinding


class ThirdPageViewModel: ViewModel() {

    private var navController: NavController?=null
    private var rsslist = MutableLiveData<MutableList<GridRss>>()
    private lateinit var adapter: RSSCard_Adapter
    val dataLoadedLiveData = MutableLiveData<Boolean>()
    @SuppressLint("StaticFieldLeak")
    var context:Context?=null

    fun loaddata(activity: Activity,context1: Context)
    {
        context?.let { getcontext(it) }
        getcontext(context1)

        rsslist.value= mutableListOf()

        rsslist.value=readRSSListFromSharedPreference(activity) as MutableList<GridRss>

        dataLoadedLiveData.value = true
    }
    fun initializefun(binding: FragmentThirdPageBinding) {

        binding.thirdpage.layoutManager = LinearLayoutManager(context)

        rsslist.value = mutableListOf()
        adapter = RSSCard_Adapter(rsslist.value!!, this::deleteRSS,this::showEditDialog)
        binding.thirdpage.adapter = adapter
        adapter.onItemClicked = this::showRSSCELL

    }



    private fun showRSSCELL(gridRss: GridRss)
    {



        val s1= gridRss.s1
        val s2= gridRss.s2
        val s3= gridRss.s3
        saveValuesToSharedPreference(s1,s2,s3, Activity())
        Log.d("s1",s1.toString())
        Log.d("s2",s2.toString())
        Log.d("s3",s3.toString())
        navController?.navigate(R.id.action_thirdPage_Fragment_to_firstPage_Fragment)

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

    private fun getcontext(context1: Context) {
        context=context1
    }

    private fun readRSSListFromSharedPreference(activity: Activity): List<GridRss> {
        val sharedPreference = activity.getSharedPreferences("sharedPreference", 0)
        val json = sharedPreference?.getString("rssList", null)
        if (json.isNullOrEmpty()) return emptyList()

        val gson = Gson()
        val type = object : TypeToken<List<GridRss>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
    private fun deleteRSS(position: Int) {
        val deletedMac = rsslist.value!![position].macAddress
        rsslist.value!!.removeAt(position)
        adapter.notifyItemRemoved(position)
        saveRSSListToSharedPreference(rsslist.value!!, Activity())

        val deletedMacs = readDeletedMacs(Activity()).toMutableSet()
        deletedMacs.add(deletedMac)
        saveDeletedMacs(deletedMacs, Activity())
    }
    private fun updateRSSList(updatedRSS: GridRss) {
        val index = rsslist.value!!.indexOfFirst { it.macAddress == updatedRSS.macAddress }
        if (index != -1) {
            rsslist.value!![index] = updatedRSS
            adapter.notifyItemChanged(index)
            saveRSSListToSharedPreference(rsslist.value!!, Activity())
        }
    }
    private fun saveDeletedMacs(deletedMacs: Set<String>,activity: Activity) {
        val gson = Gson()
        val json = gson.toJson(deletedMacs)
        val sharedPreference = activity.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor?.putString("deletedMacs", json)
        editor?.apply()
    }

    private fun readDeletedMacs(activity: Activity): Set<String> {
        val sharedPreference = activity.getSharedPreferences("sharedPreference", 0)
        val json = sharedPreference?.getString("deletedMacs", null) ?: return emptySet()

        val gson = Gson()
        val type = object : TypeToken<Set<String>>() {}.type
        return gson.fromJson(json, type) ?: emptySet()
    }
    private fun saveRSSListToSharedPreference(rssList: List<GridRss>,activity: Activity) {
        val gson = Gson()
        val json = gson.toJson(rssList)
        val sharedPreference = activity.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor?.putString("rssList", json)
        editor?.apply()
    }
    private fun saveValuesToSharedPreference(value1: Int, value2: Int,value3: Int,activity: Activity) {
        val gson = Gson()
        val sharedPreference = activity.getSharedPreferences("SelectedRSS", Context.MODE_PRIVATE)
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

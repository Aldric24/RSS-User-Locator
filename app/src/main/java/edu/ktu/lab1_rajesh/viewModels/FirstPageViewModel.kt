package edu.ktu.lab1_rajesh.viewModels

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.location.Location
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import edu.ktu.lab1_rajesh.API_Retro.RetrofitClient
import edu.ktu.lab1_rajesh.Adapters.AxisAdapter
import edu.ktu.lab1_rajesh.Adapters.MyAdapter
import edu.ktu.lab1_rajesh.R
import edu.ktu.lab1_rajesh.app_models.GridRss
import edu.ktu.lab1_rajesh.app_models.Matavimas
import edu.ktu.lab1_rajesh.app_models.Strength
import edu.ktu.lab1_rajesh.app_models.User
import edu.ktu.lab1_rajesh.app_models.stiprumai
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.sqrt

class FirstPageViewModel: ViewModel() {
    private val gridrsslivedata = MutableLiveData<MutableList<GridRss>>()
    private val matavimai = MutableLiveData<MutableList<Matavimas>>()
    private val mapset = MutableLiveData<MutableList<Pair<Int,Int>>>()
    private val rSSlist = MutableLiveData<MutableList<Strength>>()
    private  val recyclerViewM= MutableLiveData<RecyclerView>()
    private  val recyclerViewX= MutableLiveData<RecyclerView>()
    private  val recyclerViewY= MutableLiveData<RecyclerView>()

    var s1: Int = 0
    var s2: Int = 0
    var s3: Int = 0


    fun ialize(resources: Resources,activity: Activity) {
        gridrsslivedata.value = mutableListOf()
        matavimai.value = mutableListOf()
        mapset.value = mutableListOf()
        rSSlist.value = mutableListOf()
        retrieveValuesFromSharedPreference(activity)
        readMatavimai(resources)
        readRSSList(resources)
        connect()


    }
    private fun connect() {
        RetrofitClient.apiService.getMatavimai().enqueue(object : Callback<List<Location>> {
            override fun onResponse(call: Call<List<Location>>, response: Response<List<Location>>) {
                if (response.isSuccessful) {
                    Log.e("Response==", "getMatavimai=: $response ")
                    response.body()?.let { locations ->
                        val taskListDB = mutableListOf<Location>()
                        taskListDB.addAll(locations)
                        Log.d("APIResponse", "Fetched locations: $locations ")
                    }
                } else {
                    Log.e("APIError", "Response not successful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Location>>, t: Throwable) {
                Log.e("APIError", "Error fetching locations: ${t.message}")
            }
        })

        RetrofitClient.apiService.getStiprumai().enqueue(object : Callback<List<stiprumai>> {
            override fun onResponse(
                call: Call<List<stiprumai>>,
                response: Response<List<stiprumai>>
            ) {
                if (response.isSuccessful) {
                    Log.e("Response==", "getStiprumai=: $response ")
                    response.body()?.let { strengths ->
                        val strengthListDB = mutableListOf<stiprumai>()

                        strengthListDB.addAll(strengths)

                        Log.d("APIResponse", "Fetched strengths: $strengths ")
                    }
                } else {
                    Log.e("APIError", "Response not successful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<stiprumai>>, t: Throwable) {
                Log.e("APIError", "Error fetching strengths: ${t.message}")
            }
        })

        RetrofitClient.apiService.getVartojai().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    Log.e("Response==", "getVartojai=: $response ")
                    response.body()?.let { users ->
                        val userListDB = mutableListOf<User>()
                        userListDB.addAll(users)
                        Log.d("APIResponse", "Fetched users: $users ")
                    }
                } else {
                    Log.e("APIError", "Response not successful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("APIError", "Error fetching users: ${t.message}")
            }
        })


    }
    private fun readMatavimai(resources: Resources) {
        val jsonFile = resources.openRawResource(R.raw.matavimas).bufferedReader().use {
            it.readText()
        }
        val jsonArray = JSONArray(jsonFile)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.getString("type") == "table") {
                val matavimaiArray = jsonObject.getJSONArray("data")
                for (j in 0 until matavimaiArray.length()) {
                    val matavimasObject = matavimaiArray.getJSONObject(j)
                    val matavimas = matavimasObject.getInt("matavimas")
                    val x = matavimasObject.getInt("x")
                    val y = matavimasObject.getInt("y")
                    val atstumas = matavimasObject.getDouble("atstumas")
                    val location = Matavimas(matavimas, x, y, atstumas)
                    matavimai.value?.add(location)
                    mapset.value?.add(Pair(x,y))






                }
            }
        }
    }
    private fun readRSSList(resources: Resources) {
        val jsonFile = resources.openRawResource(R.raw.stiprumai).bufferedReader().use {
            it.readText()
        }

        val jsonArray = JSONArray(jsonFile)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            if (jsonObject.getString("type") == "table" && jsonObject.getString("name") == "stiprumai") {
                val data = jsonObject.getJSONArray("data")
                var j = 0
                while (j < data.length()) {
                    try {
                        val first = data.getJSONObject(j)
                        val second = data.getJSONObject(j + 1)
                        val third = data.getJSONObject(j + 2)

                        val matavimas = first.getInt("matavimas")
                        val s1 = first.getInt("stiprumas")
                        val s2 = second.getInt("stiprumas")
                        val s3 = third.getInt("stiprumas")

                        rSSlist.value?.add(Strength(matavimas, s1, s2, s3))
                        j += 3
                    } catch (e: JSONException) {
                        Log.e("JSONError", "Error reading JSON: ${e.message}")
                        break
                    }
                }
            }
        }
    }

    fun userloc(r: RecyclerView,r1: RecyclerView,r2: RecyclerView) {
        recyclerViewM.value=r
        recyclerViewX.value=r1
        recyclerViewY.value=r2

        initrecyle()

    }




private fun findUserLocation(s1: Int, s2: Int, s3: Int): Pair<Int, Int> {
    var closestMatavimai = Pair(0, 0)
    var closestDistance = Double.MAX_VALUE
    var ClosestGridCell = -1

    for (strength in rSSlist.value!!) {
        val distance = calculateEuclideanDistance(strength, s1, s2, s3)
        if (distance < closestDistance) {
            closestDistance = distance
            ClosestGridCell = strength.matavimas

        }
    }
        // Ensure that the closestMatavimas is a valid index in matavimai
        if (ClosestGridCell in 0 until matavimai.value!!.size) {
            for (point in matavimai.value!!) {
                if (point.matavimas == ClosestGridCell) {
                    closestMatavimai = Pair(point.x, point.y)

                    break  // Break out of the loop once the matching matavimas is found
                }
            }
        }

    Log.d("Closestmatavimai","${ClosestGridCell}")

    return closestMatavimai
}

    private fun calculateEuclideanDistance(rss: Strength,s1:Int,s2: Int,s3:Int ): Double {
        val dx = rss.s1 - s1
        val dy = rss.s2 - s2
        val dz = rss.s3 - s3
        return sqrt((dx * dx + dy * dy + dz * dz).toDouble())
    }

    private fun generateDataX(): List<String> {
        return (-6..6).map { if (it == -6) ""
        else
            "$it"}
    }
    private fun generateDataY(): List<String> {
        return (35 downTo -12).map { if (it == -12) ""
        else
            "$it"}
    }


    private fun retrieveValuesFromSharedPreference(activity: Activity) {
        val gson = Gson()
        val sharedPreference =
            activity.getSharedPreferences("SelectedRSS", Context.MODE_PRIVATE)

        // Retrieve serialized values from SharedPreferences
        val jsonValue1 = sharedPreference?.getString("value1", null)
        val jsonValue2 = sharedPreference?.getString("value2", null)
        val jsonValue3 = sharedPreference?.getString("value3", null)

        // Deserialize values only if they are not null
        s1 = if (jsonValue1 != null) gson.fromJson(jsonValue1, Int::class.java) else s1
        s2 = if (jsonValue2 != null) gson.fromJson(jsonValue2, Int::class.java) else s2
        s3 = if (jsonValue3 != null) gson.fromJson(jsonValue3, Int::class.java) else s3
    }

    private fun generateData(rows: Int, cols: Int): List<String> {
        val data = mutableListOf<String>()
        for (row in rows-1 downTo  0) {
            for (col in cols-1 downTo  0) {
                val x: Int = cols - 6 -col
                val y: Int = row -12
                val gridMap=Pair(x,y)
                val User=findUserLocation(s1,s2,s3)

                if (gridMap in mapset.value!! && gridMap!=User) {


                    // Represent "1" as green
                    data.add("<font color='#00FF00'>1</font>")

                }
                else if(gridMap==User) {

                   // Log.d(findUserLocation(s1,s2,s3).toString(),"")
                    // Represent "1" as green
                    data.add("<font color='#EDD500'>1</font>")
                }
                else {
                    // Represent "0" as red
                    data.add("<font color='#FF0000' style='background-color: #D50707;'>0</font>")
                }

            }
        }
        return data
    }
    fun initrecyle(){ // Create an adapter and set it to the RecyclerView
        val adapter = MyAdapter(generateData(48,12))
        recyclerViewM.value!!.adapter = adapter
        val adapterXaxis = AxisAdapter(generateDataX())
        recyclerViewX.value!!.adapter = adapterXaxis
        val adapterYaxis = AxisAdapter(generateDataY())
        recyclerViewY.value!!.adapter = adapterYaxis



        // Set the layout manager to a GridLayoutManager with 5 columns
        recyclerViewM.value!!.layoutManager = GridLayoutManager(recyclerViewM.value!!.context, 12)
        recyclerViewX.value!!.layoutManager = GridLayoutManager(recyclerViewM.value!!.context, 13)
        recyclerViewY.value!!.layoutManager = GridLayoutManager(recyclerViewM.value!!.context, 1)
        val scrollListenerFor = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerViewY.value!!.scrollBy(dx, dy)
                }
            }
        }

        val scrollListenerForY = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerViewM.value!!.scrollBy(dx, dy)
                }
            }
        }

        recyclerViewM.value!!.addOnScrollListener(scrollListenerFor)
        recyclerViewY.value!!.addOnScrollListener(scrollListenerForY)
    }

}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val textView: TextView = view.findViewById(R.id.textView)
}

package edu.ktu.lab1_rajesh.API_Retro

import android.location.Location
import edu.ktu.lab1_rajesh.app_models.GridRss
import edu.ktu.lab1_rajesh.app_models.Matavimas
import edu.ktu.lab1_rajesh.app_models.Strength
import edu.ktu.lab1_rajesh.app_models.User
import edu.ktu.lab1_rajesh.app_models.stiprumai
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApI {
    @GET("matavimai")
    fun getMatavimai(): Call<List<Location>>

    @GET("stiprumai")
    fun getStiprumai(): Call<List<stiprumai>>

    @GET("vartotojai")
    fun getVartojai(): Call<List<User>>
}


package edu.ktu.lab1_rajesh.app_models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val mac: String,
    val sensorius: String,
    val stiprumas: Int
)

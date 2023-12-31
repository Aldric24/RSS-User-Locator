package edu.ktu.lab1_rajesh.app_models

import kotlinx.serialization.Serializable

@Serializable
data class stiprumai(
    val id: Int,
    val matavimas: Int,
    val sensorius: String,
    val stiprumas: Int
)
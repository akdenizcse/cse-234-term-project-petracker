package com.example.petracker.domain.model

data class Pet(
    val name: String = "",
    val age: Int = 0,
    val hasFood: Boolean = false,
    val hasWater: Boolean = false,
    val sick: Boolean = false,
    val gotMedicine: Boolean = false,
    val sickDays: Int = 0
){

    constructor() : this("", 0, false, false, false, false, 0)
}
package com.example.appcolegio.local.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "curso")
data class Curso(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val ciclo: Int,
    val credito: Double,
    val carrera: String
)

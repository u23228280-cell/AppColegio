package com.example.appcolegio.local.entidades

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "docente")
data class Docente(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val especialidad: String,
    val correo: String
)

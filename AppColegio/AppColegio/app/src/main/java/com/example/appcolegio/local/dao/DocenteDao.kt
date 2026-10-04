package com.example.appcolegio.local.dao

import androidx.room.*
import com.example.appcolegio.local.entidades.Docente
import kotlinx.coroutines.flow.Flow

@Dao
interface DocenteDao {
    @Query("SELECT * FROM docente ORDER BY id")
    fun listar(): Flow<List<Docente>>

    @Query("SELECT * FROM docente WHERE id = :id")
    suspend fun buscar(id: Int): Docente?

    @Insert
    suspend fun insertar(docente: Docente)

    @Update
    suspend fun actualizar(docente: Docente)

    @Delete
    suspend fun eliminar(docente: Docente)
}

package com.example.appcolegio.local.dao

import androidx.room.*
import com.example.appcolegio.local.entidades.Curso
import kotlinx.coroutines.flow.Flow

@Dao
interface CursoDao {
    @Query("SELECT * FROM curso ORDER BY id")
    fun listar(): Flow<List<Curso>>

    @Query("SELECT * FROM curso WHERE id = :id")
    suspend fun buscar(id: Int): Curso?

    @Insert
    suspend fun insertar(curso: Curso)

    @Update
    suspend fun actualizar(curso: Curso)

    @Delete
    suspend fun eliminar(curso: Curso)
}

package com.example.appcolegio.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appcolegio.local.dao.CursoDao
import com.example.appcolegio.local.dao.DocenteDao
import com.example.appcolegio.local.entidades.Curso
import com.example.appcolegio.local.entidades.Docente

@Database(entities = [Curso::class, Docente::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cursoDao(): CursoDao
    abstract fun docenteDao(): DocenteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "colegio.db"
                ).build().also { INSTANCE = it }
            }
    }
}

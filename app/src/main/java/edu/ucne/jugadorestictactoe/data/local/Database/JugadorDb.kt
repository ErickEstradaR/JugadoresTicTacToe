package edu.ucne.jugadorestictactoe.data.local.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.jugadorestictactoe.data.local.Dao.JugadorDao

import edu.ucne.jugadorestictactoe.data.local.Entities.JugadorEntity

@Database(
    entities = [
        JugadorEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class JugadorDb: RoomDatabase(){
    abstract fun JugadorDao(): JugadorDao
}
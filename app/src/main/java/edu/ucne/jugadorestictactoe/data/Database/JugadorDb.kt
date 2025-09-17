package edu.ucne.jugadorestictactoe.data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.jugadorestictactoe.data.local.jugadores.Dao.JugadorDao

import edu.ucne.jugadorestictactoe.data.local.jugadores.Entities.JugadorEntity
import edu.ucne.jugadorestictactoe.data.local.partidas.Dao.PartidaDao
import edu.ucne.jugadorestictactoe.data.local.partidas.Entity.PartidaEntity

@Database(
    entities = [
        JugadorEntity::class,
        PartidaEntity::class
    ],
    version = 2,
    exportSchema = false
)

abstract class JugadorDb: RoomDatabase(){
    abstract fun JugadorDao(): JugadorDao
    abstract fun PartidaDao(): PartidaDao
}
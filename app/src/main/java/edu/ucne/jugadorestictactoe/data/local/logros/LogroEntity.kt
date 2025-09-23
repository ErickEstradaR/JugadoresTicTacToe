package edu.ucne.jugadorestictactoe.data.local.logros

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Logros")
data class LogroEntity (
    @PrimaryKey
    val logroId: Int? = null,
    val nombre: String,
    val descripcion: String,
    )
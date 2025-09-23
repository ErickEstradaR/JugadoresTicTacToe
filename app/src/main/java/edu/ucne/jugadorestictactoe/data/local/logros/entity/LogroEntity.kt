package edu.ucne.jugadorestictactoe.data.local.logros.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Logros")
data class LogroEntity (
    @PrimaryKey
    val logroId: Int? = null,
    val nombre: String,
    val descripcion: String,
    )
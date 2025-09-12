package edu.ucne.jugadorestictactoe.data.local.partidas.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Partidas")
data class PartidaEntity (
    @PrimaryKey
    val partidaId: Int? = null,
    val fecha: String,
    val jugador1Id: Int,
    val jugador2Id: Int,
    val ganadorId: Int?,
    val esFinalizada: Boolean
)

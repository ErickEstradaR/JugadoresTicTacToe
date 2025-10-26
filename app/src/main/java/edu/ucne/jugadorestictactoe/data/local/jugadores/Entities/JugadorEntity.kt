package edu.ucne.jugadorestictactoe.data.local.jugadores.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Jugadores")

data class JugadorEntity(
    @PrimaryKey
    val jugadorId: String = UUID.randomUUID().toString(),
    val remoteId: Int? = null,
    val nombres: String = "",
    val email: String = "",
    val isPendingCreate: Boolean = false
)
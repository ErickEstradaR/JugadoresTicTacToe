package edu.ucne.jugadorestictactoe.data.mappers

import edu.ucne.jugadorestictactoe.data.local.Entities.JugadorEntity
import edu.ucne.jugadorestictactoe.domain.model.Jugador


    fun JugadorEntity.toDomain() = Jugador(
        id = jugadorId ?: 0,
        nombre = nombres,
        partidas = partidas
    )

    fun Jugador.toEntity() = JugadorEntity(
        jugadorId = id,
        nombres = nombre,
        partidas = partidas
    )

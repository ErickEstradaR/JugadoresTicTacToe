package edu.ucne.jugadorestictactoe.data.local.mappers

import edu.ucne.jugadorestictactoe.data.local.jugadores.Entities.JugadorEntity
import edu.ucne.jugadorestictactoe.data.local.logros.entity.LogroEntity
import edu.ucne.jugadorestictactoe.data.local.partidas.Entity.PartidaEntity
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.model.Partida


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

    fun PartidaEntity.toDomain() = Partida(
        id = partidaId ?: 0,
        fecha = fecha,
        jugador1 = jugador1Id,
        jugador2 = jugador2Id,
        ganadorId = ganadorId,
        esFinalizada = esFinalizada
    )

    fun Partida.toEntity() = PartidaEntity(
            partidaId = id,
            fecha = fecha,
            jugador1Id = jugador1,
            jugador2,
            ganadorId = ganadorId,
            esFinalizada = esFinalizada
        )

    fun LogroEntity.toDomain() = Logro(
        id = logroId,
        nombre = nombre,
        descripcion = descripcion
    )

    fun Logro.toEntity() = LogroEntity(
        logroId = id,
        nombre = nombre,
        descripcion = descripcion
    )



package edu.ucne.jugadorestictactoe.data.local.mappers

import edu.ucne.jugadorestictactoe.data.local.jugadores.Entities.JugadorEntity
import edu.ucne.jugadorestictactoe.data.local.logros.entity.LogroEntity
import edu.ucne.jugadorestictactoe.data.local.partidas.Entity.PartidaEntity
import edu.ucne.jugadorestictactoe.data.remote.dto.jugador.JugadorResponse
import edu.ucne.jugadorestictactoe.data.remote.dto.partida.MovimientoDto
import edu.ucne.jugadorestictactoe.data.remote.dto.partida.PartidaDto
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.model.Movimiento
import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.model.PartidaApi


fun JugadorEntity.toDomain() = Jugador(
    jugadorId = jugadorId,
    remoteId = remoteId,
    nombres = nombres,
    email = email,
    isPendingCreate = isPendingCreate
)

fun Jugador.toEntity() = JugadorEntity(
    jugadorId = jugadorId,
    remoteId = remoteId,
    nombres = nombres,
    email = email,
    isPendingCreate = isPendingCreate
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

    fun PartidaDto.toDomain() = PartidaApi(
        jugador1Id = jugador1Id,
        jugador2Id = jugador2Id
    )


    fun PartidaApi.toDto() = PartidaDto(
        jugador1Id = jugador1Id,
        jugador2Id = jugador2Id,
        )

    fun Movimiento.toDto() = MovimientoDto(
        partidaId = partidaId,
        jugador = jugador,
        posicionFila = posicionFila,
        posicionColumna = posicionColumna
    )

    fun MovimientoDto.toDomain() = Movimiento(
        partidaId = partidaId,
        jugador = jugador,
        posicionFila = posicionFila,
        posicionColumna = posicionColumna
    )

    fun JugadorResponse.toDomain()= JugadorApi(
        nombres = nombres,
        email = email
    )






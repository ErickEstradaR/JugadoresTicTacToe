package edu.ucne.jugadorestictactoe.data.local.mappers

import edu.ucne.jugadorestictactoe.data.local.jugadores.Entities.JugadorEntity
import edu.ucne.jugadorestictactoe.data.local.logros.entity.LogroEntity
import edu.ucne.jugadorestictactoe.data.local.partidas.Entity.PartidaEntity
import edu.ucne.jugadorestictactoe.data.remote.dto.partida.JugadorDto
import edu.ucne.jugadorestictactoe.data.remote.dto.partida.MovimientoDto
import edu.ucne.jugadorestictactoe.data.remote.dto.partida.PartidaDto
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.model.Movimiento
import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.model.PartidaApi


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

    fun PartidaDto.toDomain() = PartidaApi (
        PartidaId = PartidaId,
        Jugador1Id = Jugador1Id,
        Jugador2Id = Jugador2Id,
        EstadoPartida = EstadoPartida,
        GanadorId = GanadorId,
        TurnoJugadorId = TurnoJugadorId,
        EstadoTablero = EstadoTablero,
        FechaInicio = FechaInicio,
        FechaFin = FechaFin,
        Jugador1 = Jugador1,
        Jugador2 = Jugador2,
        Ganador = Ganador,
        TurnoJugador = TurnoJugador,
        Movimientos = Movimientos?.map { it.toDomain() }
        )


    fun PartidaApi.toDto() = PartidaDto(
        PartidaId = PartidaId,
        Jugador1Id = Jugador1Id,
        Jugador2Id = Jugador2Id,
        EstadoPartida = EstadoPartida,
        GanadorId = GanadorId,
        TurnoJugadorId = TurnoJugadorId,
        EstadoTablero = EstadoTablero,
        FechaInicio = FechaInicio,
        FechaFin = FechaFin,
        Jugador1 = Jugador1,
        Jugador2 = Jugador2,
        Ganador = Ganador,
        TurnoJugador = TurnoJugador,
        Movimientos = Movimientos?.map { it.toDto() }
        )

    fun Movimiento.toDto() = MovimientoDto(
        MovimientoId = MovimientoId,
        PartidaId=PartidaId,
        JugadorId=JugadorId,
        PosicionFila=PosicionFila,
        PosicionColumna=PosicionColumna,
        FechaMovimiento=FechaMovimiento,
        Partida=Partida,
        Jugador=Jugador
    )

    fun MovimientoDto.toDomain() = Movimiento(
        MovimientoId = MovimientoId,
        PartidaId=PartidaId,
        JugadorId=JugadorId,
        PosicionFila=PosicionFila,
        PosicionColumna=PosicionColumna,
        FechaMovimiento=FechaMovimiento,
        Partida=Partida,
        Jugador=Jugador
    )

    fun JugadorDto.toDomain()= JugadorApi(
        JugadorId = JugadorId,
        Nombres = Nombres,
        Email = Email,
        FechaCreacion = FechaCreacion,
        Victorias = Victorias,
        Derrotas = Derrotas,
        Empates = Empates,
        PartidasComoJugador1 = PartidasComoJugador1?.map { it.toDomain() },
        PartidasComoJugador2 = PartidasComoJugador2?.map { it.toDomain() },
        PartidasGanadas = PartidasGanadas?.map { it.toDomain() },
        Movimientos = Movimientos?.map { it.toDomain() }
    )

    fun JugadorApi.toDto()= JugadorDto(
        JugadorId = JugadorId,
        Nombres = Nombres,
        Email = Email,
        FechaCreacion = FechaCreacion,
        Victorias = Victorias,
        Derrotas = Derrotas,
        Empates = Empates,
        PartidasComoJugador1 = PartidasComoJugador1?.map { it.toDto() },
        PartidasComoJugador2 = PartidasComoJugador2?.map { it.toDto() },
        PartidasGanadas = PartidasGanadas?.map { it.toDto() },
        Movimientos = Movimientos?.map { it.toDto() }
    )





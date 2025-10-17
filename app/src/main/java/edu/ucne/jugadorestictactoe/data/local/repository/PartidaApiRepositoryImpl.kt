package edu.ucne.jugadorestictactoe.data.local.repository

import edu.ucne.jugadorestictactoe.data.local.mappers.toDomain
import edu.ucne.jugadorestictactoe.data.remote.dto.partida.PartidaDto
import edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi.PartidaApiService
import edu.ucne.jugadorestictactoe.domain.model.PartidaApi
import edu.ucne.jugadorestictactoe.domain.repository.PartidaApiRepository
import javax.inject.Inject

class PartidaApiRepositoryImpl @Inject constructor(
        private val api: PartidaApiService
    ) : PartidaApiRepository {

        override suspend fun getPartidas(): List<PartidaApi> {
            return api.getPartidas().map { it.toDomain() }
        }

        override suspend fun getPartida(partidaId: Int): PartidaApi {
            return api.getPartida(partidaId).toDomain()
        }

        override suspend fun createPartida(player1Id: Int): PartidaApi {
            val newPartidaDto = PartidaDto(
                PartidaId = 0,
                Jugador1Id = player1Id,
                Jugador2Id = null,
                EstadoPartida = "PENDIENTE",
                GanadorId = null,
                TurnoJugadorId = player1Id,
                EstadoTablero = ".........",
                FechaInicio = null,
                FechaFin = null,

                Jugador1 = null,
                Jugador2 = null,
                Ganador = null,
                TurnoJugador = null,

                Movimientos = emptyList()
            )
            return api.createPartida(newPartidaDto).toDomain()
        }
    }

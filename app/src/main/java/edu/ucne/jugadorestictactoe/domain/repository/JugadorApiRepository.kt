package edu.ucne.jugadorestictactoe.domain.repository

import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import kotlinx.coroutines.flow.Flow

interface JugadorApiRepository {
    suspend fun getJugadores(): List<JugadorApi>

    suspend fun getJugador(jugadorId: Int): JugadorApi?

    suspend fun createJugador(jugador: JugadorApi)

    suspend fun updateJugador(jugador: JugadorApi)

    fun getAllFlow(): Flow<List<JugadorApi>>

}
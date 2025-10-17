package edu.ucne.jugadorestictactoe.domain.repository

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.model.JugadorApi

interface JugadorApiRepository {
    suspend fun getJugadores(): List<JugadorApi>

    suspend fun getJugador(jugadorId: Int): JugadorApi?

    suspend fun createJugador(jugador: JugadorApi)

    suspend fun updateJugador(jugador: JugadorApi)

}
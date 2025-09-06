package edu.ucne.jugadorestictactoe.domain.repository

import edu.ucne.jugadorestictactoe.domain.model.Jugador
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    suspend fun save(jugador: Jugador): Boolean
    suspend fun find(id: Int): Jugador?
    suspend fun delete(jugador: Jugador)
    suspend fun getAll(): List<Jugador>
    fun getAllFlow(): Flow<List<Jugador>>
}
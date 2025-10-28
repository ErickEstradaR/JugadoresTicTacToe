package edu.ucne.jugadorestictactoe.domain.repository

import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import kotlinx.coroutines.flow.Flow

interface JugadorRepository {
    suspend fun find(id: String): Jugador?
    fun getAllFlow(): Flow<List<Jugador>>
    suspend fun postPendingJugadores(): Resource<Unit>
    suspend fun delete(id: String) : Resource<Unit>
    suspend fun upsert(jugador: Jugador): Resource<Unit>
    suspend fun createJugadorLocal(jugador: Jugador): Resource<Jugador>

    suspend fun refreshJugadores(): Resource<Unit>
}
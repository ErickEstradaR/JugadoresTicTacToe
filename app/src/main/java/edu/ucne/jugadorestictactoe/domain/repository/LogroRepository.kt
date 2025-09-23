package edu.ucne.jugadorestictactoe.domain.repository

import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.model.Partida
import kotlinx.coroutines.flow.Flow

interface LogroRepository {
    suspend fun save(logro: Logro): Boolean
    suspend fun find(id: Int): Logro?
    suspend fun delete(logro: Logro)
    suspend fun getAll(): List<Logro>
    fun getAllFlow(): Flow<List<Logro>>
}
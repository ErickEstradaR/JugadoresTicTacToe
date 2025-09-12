package edu.ucne.jugadorestictactoe.domain.repository

import edu.ucne.jugadorestictactoe.domain.model.Partida
import kotlinx.coroutines.flow.Flow

interface PartidaRepository {
    suspend fun save(partida: Partida): Boolean
    suspend fun find(id: Int): Partida?
    suspend fun delete(partida: Partida)
    suspend fun getAll(): List<Partida>
    fun getAllFlow(): Flow<List<Partida>>
}
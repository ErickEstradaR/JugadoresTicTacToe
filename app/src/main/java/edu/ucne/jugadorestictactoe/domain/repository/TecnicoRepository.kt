package edu.ucne.jugadorestictactoe.domain.repository

import edu.ucne.jugadorestictactoe.domain.model.Tecnico
import kotlinx.coroutines.flow.Flow


interface TecnicoRepository {
    suspend fun getAllTecnicos(): List<Tecnico>
    suspend fun getTecnicoById(id: Int): Tecnico?
    suspend fun createTecnico(tecnico: Tecnico)
    suspend fun updateTecnico(tecnico: Tecnico)
    suspend fun deleteTecnico(id: Int?)
    fun getAllFlow(): Flow<List<Tecnico>>
}

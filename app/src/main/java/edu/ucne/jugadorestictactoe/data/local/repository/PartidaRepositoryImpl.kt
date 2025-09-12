package edu.ucne.jugadorestictactoe.data.local.repository

import edu.ucne.jugadorestictactoe.data.local.mappers.toDomain
import edu.ucne.jugadorestictactoe.data.local.mappers.toEntity
import edu.ucne.jugadorestictactoe.data.local.partidas.Dao.PartidaDao
import edu.ucne.jugadorestictactoe.data.local.partidas.Entity.PartidaEntity
import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PartidaRepositoryImpl @Inject constructor(
    private val dao: PartidaDao
) : PartidaRepository {

    override suspend fun save(partida: Partida): Boolean {
        dao.savePartida(partida.toEntity())
        return true
    }

    override suspend fun find (id:Int) : Partida? =
        dao.findPartida(id)?.toDomain()

    override suspend fun delete(partida: Partida) {
        dao.deletePartida(partida.toEntity())
    }

    override suspend fun getAll(): List<Partida> =
        dao.getAllPartidas().firstOrNull()?.map { it.toDomain() } ?: emptyList()

    override fun getAllFlow(): Flow<List<Partida>> =
        dao.getAllPartidas().map { entities -> entities.map(PartidaEntity::toDomain) }

}

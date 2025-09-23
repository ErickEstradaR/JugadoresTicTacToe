package edu.ucne.jugadorestictactoe.data.local.repository

import edu.ucne.jugadorestictactoe.data.local.logros.dao.LogroDao
import edu.ucne.jugadorestictactoe.data.local.logros.entity.LogroEntity
import edu.ucne.jugadorestictactoe.data.local.mappers.toDomain
import edu.ucne.jugadorestictactoe.data.local.mappers.toEntity
import edu.ucne.jugadorestictactoe.data.local.partidas.Dao.PartidaDao
import edu.ucne.jugadorestictactoe.data.local.partidas.Entity.PartidaEntity
import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.repository.LogroRepository
import edu.ucne.jugadorestictactoe.domain.repository.PartidaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LogroRepositoryImpl @Inject constructor(
    private val dao: LogroDao
) : LogroRepository {

    override suspend fun save(logro: Logro): Boolean {
        dao.saveLogro(logro.toEntity())
        return true
    }

    override suspend fun find (id:Int) : Logro? =
        dao.findLogro(id)?.toDomain()

    override suspend fun delete(logro: Logro) {
        dao.deleteLogro(logro.toEntity())
    }

    override suspend fun getAll(): List<Logro> =
        dao.getAllLogros().firstOrNull()?.map { it.toDomain() } ?: emptyList()

    override fun getAllFlow(): Flow<List<Logro>> =
        dao.getAllLogros().map { entities -> entities.map(LogroEntity::toDomain) }

}
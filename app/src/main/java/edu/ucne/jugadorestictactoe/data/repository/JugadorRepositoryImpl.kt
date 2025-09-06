package edu.ucne.jugadorestictactoe.data.repository

import edu.ucne.jugadorestictactoe.data.local.Dao.JugadorDao
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.data.mappers.toDomain
import edu.ucne.jugadorestictactoe.data.mappers.toEntity
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JugadorRepositoryImpl @Inject constructor(
    private val dao: JugadorDao
) : JugadorRepository {

    override suspend fun save(jugador: Jugador): Boolean {
        dao.save(jugador.toEntity())
        return true
    }

    override suspend fun find(id: Int): Jugador? =
        dao.find(id)?.toDomain()

    override suspend fun delete(jugador: Jugador) {
        dao.delete(jugador.toEntity())
    }

    override suspend fun getAll(): List<Jugador> =
        dao.getAll().first().map { it.toDomain() }

    override fun getAllFlow(): Flow<List<Jugador>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }
}

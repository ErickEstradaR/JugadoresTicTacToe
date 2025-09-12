package edu.ucne.jugadorestictactoe.data.local.repository

import edu.ucne.jugadorestictactoe.data.local.jugadores.Dao.JugadorDao
import edu.ucne.jugadorestictactoe.data.local.jugadores.Entities.JugadorEntity
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.data.local.mappers.toDomain
import edu.ucne.jugadorestictactoe.data.local.mappers.toEntity
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
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
        dao.getAll().firstOrNull()?.map { it.toDomain() } ?: emptyList()

    override fun getAllFlow(): Flow<List<Jugador>> =
        dao.getAll().map { entities -> entities.map(JugadorEntity::toDomain) }

}

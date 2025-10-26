package edu.ucne.jugadorestictactoe.data.local.repository

import android.util.Log
import edu.ucne.jugadorestictactoe.data.local.jugadores.Dao.JugadorDao
import edu.ucne.jugadorestictactoe.data.local.mappers.toDomain
import edu.ucne.jugadorestictactoe.data.local.mappers.toEntity
import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.data.remote.dto.JugadorRemoteDataSource
import edu.ucne.jugadorestictactoe.data.remote.dto.jugador.JugadorRequest
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class JugadorApiRepositoryImpl @Inject constructor(
    private val localDataSource: JugadorDao,
    private val remoteDataSource: JugadorRemoteDataSource
) : JugadorRepository {

    override suspend fun createJugadorLocal(jugador: Jugador): Resource<Jugador> {
        val pending = jugador.copy(isPendingCreate = true)
        localDataSource.upsert(pending.toEntity())
        return Resource.Success(pending)
    }

    override suspend fun find(id: String): Jugador? {
        return localDataSource.find(id)?.toDomain()
    }

    override fun getAllFlow(): Flow<List<Jugador>> {
        return localDataSource.observeJugadores().map { list ->
            list.map { it.toDomain() }
        }
    }


    override suspend fun upsert(jugador: Jugador): Resource<Unit> {
        val remoteId = jugador.remoteId ?: return Resource.Error("No remoteId")
        val request = JugadorRequest(jugador.nombres, jugador.email)
        return when (val result = remoteDataSource.updateJugador(remoteId, request)) {
            is Resource.Success -> {
                localDataSource.upsert(jugador.toEntity())
                Resource.Success(Unit)
            }
            is Resource.Error -> result
            else -> Resource.Loading()
        }
    }

    override suspend fun delete(id: String): Resource<Unit> {
        val jugador = localDataSource.find(id) ?: return Resource.Error("No encontrada")
        val remoteId = jugador.remoteId ?: return Resource.Error("No remoteId")
        return when (val result = remoteDataSource.deleteJugador(remoteId)) {
            is Resource.Success -> {
                localDataSource.delete(id)
                Resource.Success(Unit)
            }
            is Resource.Error -> result
            else -> Resource.Loading()
        }
    }

    override suspend fun postPendingJugadores(): Resource<Unit> {
        val pending = localDataSource.getPendingCreateJugadores()
        for (jugador in pending) {
            val request = JugadorRequest(jugador.nombres, jugador.email)
            when (val result = remoteDataSource.createJugador(request)) {
                is Resource.Success -> {
                    val remoteId = result.data?.jugadorId
                    if (remoteId != null) {
                        val synced = jugador.copy(remoteId = remoteId, isPendingCreate = false)
                        localDataSource.upsert(synced)
                    } else {
                        Log.e("SyncWorker", "jugadorId nulo para ${jugador.jugadorId}")
                        return Resource.Error("Falló sincronización de ${jugador.nombres}: jugadorId nulo")
                    }
                }
                is Resource.Error -> {
                    Log.e("SyncWorker", "Error sincronizando jugador ${jugador.jugadorId}: ${result.message}")
                    return Resource.Error("Falló sincronización de ${jugador.nombres}: ${result.message}")
                }
                else -> {}
            }
        }
        return Resource.Success(Unit)
    }

}

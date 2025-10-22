package edu.ucne.jugadorestictactoe.data.local.repository

import edu.ucne.jugadorestictactoe.data.local.mappers.toDomain
import edu.ucne.jugadorestictactoe.data.local.mappers.toDto
import edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi.JugadorApiService
import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import edu.ucne.jugadorestictactoe.domain.repository.JugadorApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class JugadorApiRepositoryImpl @Inject constructor(
    private val apiService: JugadorApiService
) : JugadorApiRepository {

    override suspend fun getJugadores(): List<JugadorApi> {
        return apiService.getJugadores().map { it.toDomain() }
    }


    override suspend fun getJugador(jugadorId: Int): JugadorApi {
        return apiService.getJugador(jugadorId).toDomain()
    }



    override suspend fun createJugador(jugador: JugadorApi) {
        apiService.createJugador(jugador.toDto())
    }



    override fun getAllFlow(): Flow<List<JugadorApi>> = flow {
        try {
            val JugadorApi = apiService.getJugadores()
            emit(JugadorApi.map { it.toDomain() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}

package edu.ucne.jugadorestictactoe.data.remote.dto

import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.data.remote.dto.jugador.JugadorRequest
import edu.ucne.jugadorestictactoe.data.remote.dto.jugador.JugadorResponse
import edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi.JugadorApiService
import javax.inject.Inject

class JugadorRemoteDataSource @Inject constructor(
    private val api: JugadorApiService
) {
    suspend fun createJugador(request: JugadorRequest): Resource<JugadorResponse> {
        return try {
            val response = api.createJugador(request)
            if (response.isSuccessful) {
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("Respuesta vacía del servidor")
            } else {
                Resource.Error("HTTP ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red")
        }
    }

    suspend fun updateJugador(id: Int, request: JugadorRequest): Resource<Unit> {
        return try {
            val response = api.updateJugador(id, request)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("HTTP ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red")
        }
    }

    suspend fun deleteJugador(id: Int): Resource<Unit> {
        return try {
            val response = api.deleteJugador(id)
            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                Resource.Error("HTTP ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red")
        }
    }

    suspend fun getJugador(id: Int): Resource<JugadorResponse> {
        return try {
            val response = api.getJugador(id)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red al obtener el jugador")
        }
    }

    suspend fun getJugadores(): Resource<List<JugadorResponse>> {
        return try {
            val response = api.getJugadores()
            if (response.isSuccessful) {
                response.body()?.let { Resource.Success(it) }
                    ?: Resource.Error("Respuesta vacía al obtener lista de jugadores")
            } else {
                Resource.Error("HTTP ${response.code()} al obtener lista de jugadores: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Error de red al obtener jugadores")
        }
    }
}
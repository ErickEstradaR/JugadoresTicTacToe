package edu.ucne.jugadorestictactoe.data.local.repository

import edu.ucne.jugadorestictactoe.data.local.mappers.toDomain
import edu.ucne.jugadorestictactoe.data.local.mappers.toDto
import edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi.PartidaApiService
import edu.ucne.jugadorestictactoe.domain.model.PartidaApi
import edu.ucne.jugadorestictactoe.domain.repository.PartidaApiRepository
import javax.inject.Inject

class PartidaApiRepositoryImpl @Inject constructor(
        private val api: PartidaApiService
    ) : PartidaApiRepository {

        override suspend fun getPartidas(): List<PartidaApi> {
            return api.getPartidas().map { it.toDomain() }
        }

        override suspend fun getPartida(partidaId: Int): PartidaApi {
            return api.getPartida(partidaId).toDomain()
        }

    override suspend fun createPartida(partidaApi: PartidaApi): PartidaApi {
        val responseDto = api.createPartida(partidaApi.toDto())
        return responseDto.toDomain()
    }
    }

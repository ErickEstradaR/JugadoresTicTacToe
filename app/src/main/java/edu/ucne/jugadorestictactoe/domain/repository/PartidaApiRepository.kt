package edu.ucne.jugadorestictactoe.domain.repository

import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.model.PartidaApi

interface PartidaApiRepository {

    suspend fun getPartidas(): List<PartidaApi>

    suspend fun getPartida(partidaId: Int): PartidaApi

    suspend fun createPartida(player1Id: Int): PartidaApi
}
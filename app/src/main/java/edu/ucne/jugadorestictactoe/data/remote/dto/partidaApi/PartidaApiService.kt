package edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi

import edu.ucne.jugadorestictactoe.data.remote.dto.partida.PartidaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PartidaApiService {

    @GET("api/Partidas")
    suspend fun getPartidas(): List<PartidaDto>

    @GET("api/Partidas/{id}")
    suspend fun getPartida(@Path("id") id: Int): PartidaDto

    @POST("api/Partidas")
    suspend fun createPartida(@Body partida: PartidaDto): PartidaDto
}
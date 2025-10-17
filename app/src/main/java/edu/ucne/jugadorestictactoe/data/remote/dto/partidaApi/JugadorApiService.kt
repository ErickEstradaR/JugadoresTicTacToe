package edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi

import edu.ucne.jugadorestictactoe.data.remote.dto.partida.JugadorDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JugadorApiService {
    @GET("api/Jugadores")
    suspend fun getJugadores(): List<JugadorDto>

    @GET("api/Jugadores/{id}")
    suspend fun getJugador(@Path("id") id: Int): JugadorDto

    @POST("api/Jugadores")
    suspend fun createJugador(@Body jugador: JugadorDto): JugadorDto

    @PUT("api/Jugadores/{id}")
    suspend fun updateJugador(@Path("id") id: Int?, @Body jugador: JugadorDto)
}
package edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi

import edu.ucne.jugadorestictactoe.data.remote.dto.jugador.JugadorRequest
import edu.ucne.jugadorestictactoe.data.remote.dto.jugador.JugadorResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JugadorApiService {
    @GET("api/Jugadores")
    suspend fun getJugadores(): List<JugadorResponse>

    @GET("api/Jugadores/{id}")
    suspend fun getJugador(@Path("id") id: Int): JugadorResponse
    @POST("api/Jugadores")
    suspend fun createJugador(@Body request: JugadorRequest): Response<JugadorResponse>

    @DELETE("api/Jugadores/{id}")
    suspend fun deleteJugador(@Path("id") id: Int): Response<Unit>

    @PUT("api/Jugadores/{id}")
    suspend fun updateJugador(@Path("id") id: Int, @Body request: JugadorRequest): Response<Unit>

}
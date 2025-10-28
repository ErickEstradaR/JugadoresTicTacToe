package edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi

import edu.ucne.jugadorestictactoe.data.remote.dto.partida.MovimientoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovimientoApiService {

    @GET("api/Movimientos/{partidaId}")
    suspend fun getMovimientos(@Path("partidaId") partidaId: Int): List<MovimientoDto>

    @POST("api/Movimientos")
    suspend fun postMovimiento(@Body movimiento: MovimientoDto): MovimientoDto
}
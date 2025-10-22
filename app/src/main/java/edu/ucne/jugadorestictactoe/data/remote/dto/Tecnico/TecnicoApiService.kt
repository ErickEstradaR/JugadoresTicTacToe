package edu.ucne.jugadorestictactoe.data.remote.dto.Tecnico

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TecnicosApiService {

    @GET("api/Tecnicoes")
    suspend fun getTecnicos(): List<TecnicoApi>

    @GET("api/Tecnicoes/{id}")
    suspend fun getTecnico(@Path("id") id: Int): TecnicoApi

    @POST("api/Tecnicoes")
    suspend fun createTecnico(@Body tecnico: TecnicoApi): TecnicoApi

    @PUT("api/Tecnicoes/{id}")
    suspend fun updateTecnico(@Path("id") id: Int?, @Body tecnico: TecnicoApi)

    @DELETE("api/Tecnicoes/{id}")
    suspend fun deleteTecnico(@Path("id") id: Int?)
}

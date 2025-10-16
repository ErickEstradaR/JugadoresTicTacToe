package edu.ucne.jugadorestictactoe.data.local.repository

import edu.ucne.jugadorestictactoe.data.local.mappers.toDomain
import edu.ucne.jugadorestictactoe.data.local.mappers.toDto
import edu.ucne.jugadorestictactoe.data.remote.dto.partidaApi.MovimientoApiService
import edu.ucne.jugadorestictactoe.domain.model.Movimiento
import edu.ucne.jugadorestictactoe.domain.repository.MovimientoRepository
import javax.inject.Inject

class MovimientoApiRepositoryImpl @Inject constructor(
    private val api: MovimientoApiService
) : MovimientoRepository {

    override suspend fun getMovimientos(partidaId: Int): List<Movimiento> {
        return try {
            api.getMovimientos(partidaId).map { it.toDomain() }
        } catch (e: Exception) {
             println("Error al obtener movimientos: ${e.message}")
            emptyList()
        }
    }

    override suspend fun postMovimiento(movimiento: Movimiento): Movimiento {

        val movimientoDto = movimiento.toDto()

        return try {
            val postedMovimientoDto = api.postMovimiento(movimientoDto)
            postedMovimientoDto.toDomain()
        } catch (e: Exception) {
            println("Error al registrar movimiento: ${e.message}")
            throw e
        }
    }
}

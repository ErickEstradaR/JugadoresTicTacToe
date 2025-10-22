package edu.ucne.jugadorestictactoe.domain.repository

import edu.ucne.jugadorestictactoe.domain.model.Movimiento


interface MovimientoRepository {

    suspend fun getMovimientos(partidaId: Int): List<Movimiento>

    suspend fun postMovimiento(movimiento: Movimiento): Movimiento
}
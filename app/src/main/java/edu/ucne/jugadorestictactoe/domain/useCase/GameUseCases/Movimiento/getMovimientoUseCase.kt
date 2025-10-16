package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.Movimiento

import edu.ucne.jugadorestictactoe.domain.model.Movimiento
import edu.ucne.jugadorestictactoe.domain.repository.MovimientoRepository
import javax.inject.Inject

class GetMovimientosUseCase @Inject constructor(
    private val repository: MovimientoRepository
) {
    suspend operator fun invoke(partidaId: Int): List<Movimiento> {
        return repository.getMovimientos(partidaId)
    }
}
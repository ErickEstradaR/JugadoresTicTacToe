package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.Movimiento

import edu.ucne.jugadorestictactoe.domain.model.Movimiento
import edu.ucne.jugadorestictactoe.domain.repository.MovimientoRepository
import javax.inject.Inject

class PostMovimientoUseCase @Inject constructor(
    private val repository: MovimientoRepository
) {
    suspend operator fun invoke(movimiento: Movimiento): Movimiento {
        return repository.postMovimiento(movimiento)
    }
}
package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.MovimientoUseCases

import edu.ucne.jugadorestictactoe.domain.model.Movimiento
import edu.ucne.jugadorestictactoe.domain.repository.MovimientoRepository
import javax.inject.Inject



class RegistrarMovimientoUseCase @Inject constructor(
    private val movimientoRepository: MovimientoRepository // Dependencia de la interfaz
) {

    suspend operator fun invoke(movimiento: Movimiento): Movimiento {


        return movimientoRepository.postMovimiento(movimiento)
    }
}
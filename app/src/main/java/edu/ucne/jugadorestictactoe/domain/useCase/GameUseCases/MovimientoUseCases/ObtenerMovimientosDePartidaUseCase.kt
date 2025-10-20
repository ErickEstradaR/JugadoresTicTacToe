package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.MovimientoUseCases

import edu.ucne.jugadorestictactoe.domain.model.Movimiento
import edu.ucne.jugadorestictactoe.domain.repository.MovimientoRepository
import javax.inject.Inject



class ObtenerMovimientosDePartidaUseCase @Inject constructor(
    private val movimientoRepository: MovimientoRepository // Dependencia de la interfaz
) {
    suspend operator fun invoke(partidaId: Int): List<Movimiento> {

        if (partidaId <= 0) {
            return emptyList()
        }
        return movimientoRepository.getMovimientos(partidaId)
    }
}
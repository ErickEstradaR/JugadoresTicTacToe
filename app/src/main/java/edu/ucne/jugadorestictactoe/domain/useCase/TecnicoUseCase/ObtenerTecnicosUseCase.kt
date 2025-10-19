package edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase

import edu.ucne.jugadorestictactoe.domain.model.Tecnico
import edu.ucne.jugadorestictactoe.domain.repository.TecnicoRepository
import kotlinx.coroutines.flow.Flow

class ObtenerTecnicosUseCase(
    private val repository: TecnicoRepository
) {
    operator fun invoke(): Flow<List<Tecnico>> {
        return repository.getAllFlow()
    }
}

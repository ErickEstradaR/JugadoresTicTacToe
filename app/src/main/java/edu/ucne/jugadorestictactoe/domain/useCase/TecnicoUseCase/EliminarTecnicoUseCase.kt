package edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase

import edu.ucne.jugadorestictactoe.domain.repository.TecnicoRepository

class EliminarTecnicoUseCase(
    private val repository: TecnicoRepository
) {
    suspend operator fun invoke(tecnicoId: Int?) {
        repository.deleteTecnico(tecnicoId)
    }
}



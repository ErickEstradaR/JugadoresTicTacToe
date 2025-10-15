package edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase

import edu.ucne.jugadorestictactoe.domain.model.Tecnico
import edu.ucne.jugadorestictactoe.domain.repository.TecnicoRepository

class GuardarTecnicoUseCase(
    private val repository: TecnicoRepository
) {
    suspend operator fun invoke(tecnico: Tecnico) {
        if (tecnico.tecnicoId == 0) {

            repository.createTecnico(tecnico)
        } else {
            repository.updateTecnico(tecnico)
        }
    }
}
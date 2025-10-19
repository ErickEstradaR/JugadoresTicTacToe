package edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase

import edu.ucne.jugadorestictactoe.domain.model.Tecnico
import edu.ucne.jugadorestictactoe.domain.repository.TecnicoRepository



class ObtenerTecnicoUseCase(
    private val repository: TecnicoRepository
) {
    suspend operator fun invoke(id: Int): Tecnico? {
        return repository.getTecnicoById(id)
    }
}



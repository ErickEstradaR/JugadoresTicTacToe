package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.Partida

import edu.ucne.jugadorestictactoe.domain.model.PartidaApi
import edu.ucne.jugadorestictactoe.domain.repository.PartidaApiRepository
import javax.inject.Inject


class GetPartidasUseCase @Inject constructor(
    private val repository: PartidaApiRepository
) {
    suspend operator fun invoke(): List<PartidaApi> {
        return repository.getPartidas()
    }
}
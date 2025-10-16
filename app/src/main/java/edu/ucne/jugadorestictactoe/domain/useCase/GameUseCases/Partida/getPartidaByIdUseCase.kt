package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.Partida

import edu.ucne.jugadorestictactoe.domain.model.PartidaApi
import edu.ucne.jugadorestictactoe.domain.repository.PartidaApiRepository
import javax.inject.Inject

class GetPartidaByIdUseCase @Inject constructor(
    private val repository: PartidaApiRepository
) {
    suspend operator fun invoke(partidaId: Int): PartidaApi {
        return repository.getPartida(partidaId)
    }
}

package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.PartidaUseCases

import edu.ucne.jugadorestictactoe.domain.model.PartidaApi
import edu.ucne.jugadorestictactoe.domain.repository.PartidaApiRepository
import javax.inject.Inject

class ObtenerPartidaApiUseCase @Inject constructor(
    private val partidaApiRepository: PartidaApiRepository
) {

    suspend operator fun invoke(partidaId: Int): PartidaApi {

        if (partidaId <= 0) {
            throw IllegalArgumentException("El ID de la partida debe ser positivo.")
        }
        val partida = partidaApiRepository.getPartida(partidaId)

        return partida
    }
}
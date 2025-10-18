package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.PartidaUseCases

import edu.ucne.jugadorestictactoe.domain.model.PartidaApi
import edu.ucne.jugadorestictactoe.domain.repository.PartidaApiRepository
import javax.inject.Inject

class CrearPartidaUseCase @Inject constructor(
    private val partidaApiRepository: PartidaApiRepository
) {

    suspend operator fun invoke(jugador1Id: Int): PartidaApi {

        if (jugador1Id <= 0) {
            throw IllegalArgumentException("El ID del jugador debe ser positivo.")
        }

        val partidaApi = partidaApiRepository.createPartida(jugador1Id)

        return partidaApi
    }
}
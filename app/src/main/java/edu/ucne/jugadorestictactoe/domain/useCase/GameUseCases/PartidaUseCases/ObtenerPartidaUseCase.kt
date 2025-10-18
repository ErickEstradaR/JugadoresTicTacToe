package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.PartidaUseCases

import edu.ucne.jugadorestictactoe.domain.model.PartidaApi
import edu.ucne.jugadorestictactoe.domain.repository.PartidaApiRepository
import javax.inject.Inject

class ObtenerPartidaUseCase @Inject constructor(
    private val partidaApiRepository: PartidaApiRepository // Inyecta la interfaz del Repositorio
) {

    suspend operator fun invoke(partidaId: Int): PartidaApi { // Debe retornar el modelo de Dominio (Partida)

        if (partidaId <= 0) {
            throw IllegalArgumentException("El ID de la partida debe ser positivo.")
        }
        val partida = partidaApiRepository.getPartida(partidaId)

        return partida
    }
}
package edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.PartidaUseCases

import edu.ucne.jugadorestictactoe.domain.model.PartidaApi
import edu.ucne.jugadorestictactoe.domain.repository.PartidaApiRepository
import javax.inject.Inject

class CrearPartidaUseCase @Inject constructor(
    private val repository: PartidaApiRepository
){

    suspend operator fun invoke(partida: PartidaApi): PartidaApi {
        return repository.createPartida(partida)
    }
}

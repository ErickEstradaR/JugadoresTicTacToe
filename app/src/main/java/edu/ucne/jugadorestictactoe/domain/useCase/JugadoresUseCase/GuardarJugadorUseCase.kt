package edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase

import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.repository.JugadorRepository
import javax.inject.Inject


class GuardarJugadorUseCase @Inject constructor(
    private val repo: JugadorRepository,
    ) {
        suspend operator fun invoke(jugador: Jugador): Resource<Unit> = repo.upsert(jugador)
    }


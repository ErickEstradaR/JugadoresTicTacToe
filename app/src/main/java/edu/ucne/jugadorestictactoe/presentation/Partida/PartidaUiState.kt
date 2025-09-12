package edu.ucne.jugadorestictactoe.presentation.Partida

import edu.ucne.jugadorestictactoe.domain.model.Partida

data class PartidaUiState(
    val id: Int? = null,
    val fecha: String = "",
    val jugador1: Int = 0,
    val jugador2: Int = 0,
    val ganadorId: Int? = null,
    val esFinalizada: Boolean = false,

    val errorMessage: String? = null,
    val partidas: List<Partida> = emptyList()
) {
    companion object {
        fun default() = PartidaUiState()
    }
}

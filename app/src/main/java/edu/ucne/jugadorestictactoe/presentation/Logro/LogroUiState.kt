package edu.ucne.jugadorestictactoe.presentation.Logro

import edu.ucne.jugadorestictactoe.domain.model.Logro

data class LogroUiState (
    val logroId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val errorMessage: String? = null,
    val logros: List<Logro> = emptyList()
) {
    companion object {
        fun default() = LogroUiState()
    }
}
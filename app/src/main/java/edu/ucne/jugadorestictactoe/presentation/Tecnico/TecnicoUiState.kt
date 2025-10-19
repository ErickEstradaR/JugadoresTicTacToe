package edu.ucne.jugadorestictactoe.presentation.Tecnico


import edu.ucne.jugadorestictactoe.domain.model.Tecnico

data class TecnicoUiState(
    val tecnicoId: Int? = null,
    val nombre: String = "",
    val salarioHora: Double? = null,
    val errorMessage: String? = null,
    val tecnicos: List<Tecnico> = emptyList()
) {
    companion object {
        fun default() = TecnicoUiState()
    }
}

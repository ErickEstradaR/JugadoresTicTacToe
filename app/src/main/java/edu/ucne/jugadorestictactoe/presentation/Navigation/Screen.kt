package edu.ucne.jugadorestictactoe.presentation.Navigation
import kotlinx.serialization.Serializable
@Serializable
sealed class Screen{
    @Serializable
    data class Jugador(val Id: Int?): Screen()

    @Serializable
    data object List: Screen()
}
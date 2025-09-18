package edu.ucne.jugadorestictactoe.presentation.Navigation
import kotlinx.serialization.Serializable
@Serializable
sealed class Screen{
    @Serializable
    data class Jugador(val Id: Int?): Screen()
    @Serializable
    data class Partida(val Id: Int?): Screen()

    @Serializable
    data object List: Screen()
    @Serializable
    data object PartidaList: Screen()

    @Serializable
    data object GameScreen : Screen()
}
package edu.ucne.jugadorestictactoe.presentation.jugador.edit


import edu.ucne.jugadorestictactoe.presentation.Jugador.JugadorBodyScreen
import edu.ucne.jugadorestictactoe.presentation.Jugador.JugadorEvent
import edu.ucne.jugadorestictactoe.presentation.Jugador.JugadorUiState
import org.junit.Rule
import org.junit.Test

/*
class editJugadorScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun userCanTypeAndSave() {
        var lastEvent: JugadorEvent? = null
        composeRule.setContent {
            JugadorBodyScreen(
                uiState = JugadorUiState(
                    jugadorId = 0,
                    nombres = "",
                    partidas = 0,
                    errorMessage = null
                ),
                onAction = { lastEvent = it },
                goback = {},
                saveJugador = {}
            )
        }


        composeRule.onNodeWithTag("input_nombres").assertIsDisplayed().performTextInput("Juan")
        composeRule.onNodeWithTag("input_partidas").assertIsDisplayed().performTextInput("5")
        composeRule.onNodeWithTag("btn_guardar").performClick()


        assert(lastEvent is JugadorEvent.save)
    }

    @Test
    fun limpiarButtonEmitsNewEvent() {
        var lastEvent: JugadorEvent? = null
        composeRule.setContent {
            JugadorBodyScreen(
                uiState = JugadorUiState(
                    jugadorId = 1,
                    nombres = "Juan",
                    partidas = 3,
                    errorMessage = null
                ),
                onAction = { lastEvent = it },
                goback = {},
                saveJugador = {}
            )
        }

        composeRule.onNodeWithTag("btn_limpiar").performClick()
        assert(lastEvent is JugadorEvent.new)
    }
}
*/
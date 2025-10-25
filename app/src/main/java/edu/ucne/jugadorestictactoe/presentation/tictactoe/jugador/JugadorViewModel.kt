package edu.ucne.jugadorestictactoe.presentation.tictactoe.jugador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.domain.model.Jugador
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.CreateJugadorLocalUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.EliminarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.GuardarJugadorUseCase
import edu.ucne.jugadorestictactoe.domain.useCase.JugadoresUseCase.TriggerSyncUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JugadorViewModel @Inject constructor(
    private val createJugadorLocalUseCase: CreateJugadorLocalUseCase,
    private val upsertJugador: GuardarJugadorUseCase,
    private val deleteTaskUseCase: EliminarJugadorUseCase,
    private val triggerSyncUseCase: TriggerSyncUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(JugadorUiState(isLoading = true))
    val state: StateFlow<JugadorUiState> = _state.asStateFlow()

    fun onEvent(event: JugadorEvent) {
        when (event) {
            is JugadorEvent.CrearJugador -> crearJugador(event.nombres)
            is JugadorEvent.UpdateJugador -> updateJugador(event.jugador)
            is JugadorEvent.DeleteJugador -> deleteJugador(event.id)
            is JugadorEvent.ShowCreateSheet -> _state.update { it.copy(showCreateSheet = true) }
            is JugadorEvent.HideCreateSheet -> _state.update { it.copy(showCreateSheet = false, jugadorDescription = "") }
            is JugadorEvent.OnDescriptionChange -> _state.update { it.copy(jugadorDescription = event.description) }
            is JugadorEvent.UserMessageShown -> clearMessage()
        }
    }

    private fun crearJugador(nombres: String) = viewModelScope.launch {
        val jugador = Jugador(nombres = nombres , email = "string")
        when (val result = createJugadorLocalUseCase(jugador)) {
            is Resource.Success -> {
                _state.update { it.copy(userMessage = "Jugador guardado localmente", showCreateSheet = false, jugadorDescription = "") }
                triggerSyncUseCase()
            }
            is Resource.Error -> _state.update { it.copy(userMessage = result.message) }
            else -> {}
        }
    }

    private fun updateJugador(jugador: Jugador) = viewModelScope.launch {
        when (val result = upsertJugador(jugador)) {
            is Resource.Success -> _state.update { it.copy(userMessage = "Jugador actualizado") }
            is Resource.Error -> _state.update { it.copy(userMessage = result.message) }
            else -> {}
        }
    }

    private fun deleteJugador(id: String) = viewModelScope.launch {
        when (val result = deleteTaskUseCase(id)) {
            is Resource.Success -> _state.update { it.copy(userMessage = "Jugador eliminado") }
            is Resource.Error -> _state.update { it.copy(userMessage = result.message) }
            else -> {}
        }
    }

    private fun clearMessage() {
        _state.update { it.copy(userMessage = null) }
    }


}
package edu.ucne.jugadorestictactoe.presentation.tictactoe.jugador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jugadorestictactoe.data.remote.Resource
import edu.ucne.jugadorestictactoe.domain.model.JugadorApi
import edu.ucne.jugadorestictactoe.domain.useCase.GameUseCases.JugadorApiUseCases.JugadoresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JugadorViewModel @Inject constructor(
    private val useCases: JugadoresUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(JugadorUiState.default())
    private val DEFAULT_ERROR_MESSAGE = "Error desconocido"
    val uiState = _uiState.asStateFlow()

    init {
        getJugadores()
    }

    fun onEvent(event: JugadorEvent){
        when(event){
            is JugadorEvent.nombreChange -> onNombreChange(event.nombre)
            is JugadorEvent.emailChange -> onEmailChange(event.email)
            is JugadorEvent.jugadorIdChange -> onJugadorIdChange(event.jugadorId)
            JugadorEvent.save -> viewModelScope.launch { saveJugador() }
            JugadorEvent.new -> nuevo()
        }
    }

    private fun nuevo() {
        _uiState.value = JugadorUiState.default()
    }

     fun getJugadores() {
        _uiState.update { it.copy(jugadoresResource = Resource.Loading())}
        viewModelScope.launch {
            try {
                useCases.obtenerJugadores().collectLatest { lista ->
                    _uiState.update {
                        it.copy(jugadoresResource = Resource.Success(lista))
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        actionMessage = e.message ?: DEFAULT_ERROR_MESSAGE,
                        jugadoresResource = Resource.Error(e.message ?: DEFAULT_ERROR_MESSAGE, emptyList())
                    )  }
            }
        }
    }


    fun findJugador(jugadorId: Int) {
        viewModelScope.launch {
            if (jugadorId > 0) {
                try {
                    val jugador = useCases.obtenerJugador(jugadorId)
                    _uiState.update {
                        it.copy(
                            Nombres = jugador?.nombres.orEmpty(),
                            Email = jugador?.email.orEmpty(),
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = e.message ?: DEFAULT_ERROR_MESSAGE) }
                }
            }
        }
    }

    suspend fun saveJugador(): Boolean {
        val currentState = _uiState.value
        val jugador = JugadorApi(
        nombres = currentState.Nombres,
        email = currentState.Email,
        )

        return try {
            useCases.guardarJugador(jugador)

            _uiState.update { it.copy(actionMessage = "Jugador guardado exitosamente.") }

            getJugadores()

            _uiState.update { it.copy(isSaving = false) }
            true
        } catch (e: Exception) {
            _uiState.update { it.copy(
                actionMessage = e.message ?: DEFAULT_ERROR_MESSAGE,
                isSaving = false
            ) }
            false
        }
    }

    private fun onNombreChange(nombre: String){
        _uiState.update { it.copy(Nombres = nombre) }
    }

    private fun onEmailChange(email: String){
        _uiState.update { it.copy(Email = email) }
    }

    private fun onJugadorIdChange(jugadorId: Int?){
        _uiState.update { it.copy(JugadorId = jugadorId) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
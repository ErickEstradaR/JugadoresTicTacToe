package edu.ucne.jugadorestictactoe.presentation.Jugador

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jugadorestictactoe.domain.useCase.JugadorUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JugadorViewModel @Inject constructor(
    private val useCases: JugadorUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(JugadorUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getJugadores()
    }

    fun OnEvent(event: JugadorEvent){
        when(event){
            is JugadorEvent.NombreChange -> OnNombresChange(event.nombres)
            is JugadorEvent.JugadorChange -> OnJugadorChange(event.jugadorId)
            is JugadorEvent.PartidaChange -> OnPartidasChange(event.partidas)
            JugadorEvent.delete -> deleteJugador()
            JugadorEvent.new -> Nuevo()
            JugadorEvent.save -> viewModelScope.launch { saveJugador() }
        }
    }

    private fun Nuevo(){
        _uiState.update {
            it.copy(
                jugadorId = null,
                nombres = "",
                partidas = 0,
                errorMessage = ""
            )
        }
    }

    private fun getJugadores() {
        viewModelScope.launch {
            useCases.obtenerJugadores().collect { jugadores ->
                _uiState.update { it.copy(jugadores = jugadores) }
            }
        }
    }

    fun findJugador(jugadorId: Int){
        viewModelScope.launch {
            if(jugadorId > 0){
                val jugador = useCases.obtenerJugador(jugadorId)
                _uiState.update {
                    it.copy(
                        jugadorId = jugador?.id,
                        nombres = jugador?.nombre ?: "",
                        partidas = jugador?.partidas ?: 0
                    )
                }
            }
        }
    }

    suspend fun saveJugador(): Boolean {
        val currentState = _uiState.value
        val jugador = edu.ucne.jugadorestictactoe.domain.model.Jugador(
            id = currentState.jugadorId,
            nombre = currentState.nombres ?: "",
            partidas = currentState.partidas ?: 0
        )

        val result = useCases.guardarJugador(jugador)
        return result.fold(
            onSuccess = { true },
            onFailure = { e ->
                _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
                false
            }
        )
    }

    private fun deleteJugador(){
        viewModelScope.launch {
            val jugador = edu.ucne.jugadorestictactoe.domain.model.Jugador(
                id = _uiState.value.jugadorId,
                nombre = _uiState.value.nombres ?: "",
                partidas = _uiState.value.partidas ?: 0
            )
            useCases.eliminarJugador(jugador)
            getJugadores() // refrescar lista
        }
    }

    private fun OnPartidasChange(partidas: Int){
        _uiState.update { it.copy(partidas = partidas) }
    }

    private fun OnNombresChange(nombres: String){
        _uiState.update { it.copy(nombres = nombres) }
    }

    private fun OnJugadorChange(jugadorId: Int){
        _uiState.update { it.copy(jugadorId = jugadorId) }
    }
}

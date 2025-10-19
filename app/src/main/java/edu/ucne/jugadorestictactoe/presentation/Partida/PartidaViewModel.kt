package edu.ucne.jugadorestictactoe.presentation.Partida

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jugadorestictactoe.domain.model.Partida
import edu.ucne.jugadorestictactoe.domain.useCase.PartidasUseCase.PartidaUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class PartidaViewModel @Inject constructor(
    private val useCases: PartidaUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(PartidaUiState.default())
    val uiState = _uiState.asStateFlow()

    init {
        getPartidas()
    }

    fun onEvent(event: PartidaEvent){
        when(event){
            is PartidaEvent.partidaChange -> onPartidaChange(event.partidaId)
            is PartidaEvent.fechaChange -> onFechaChange(event.fecha)
            is PartidaEvent.jugador1Change -> onJugador1Change(event.jugador1)
            is PartidaEvent.jugador2Change -> onJugador2Change(event.jugador2)
            is PartidaEvent.ganadorChange -> onGanadorChange(event.ganadorId)
            is PartidaEvent.esFinalizadaChange -> onEsFinalizadaChange(event.esFinalizada)
            PartidaEvent.delete -> deletePartida()
            PartidaEvent.new -> nuevo()
            PartidaEvent.save -> viewModelScope.launch { savePartida() }
        }
    }

    private fun nuevo() {
        _uiState.value = PartidaUiState.default()
    }

    private fun getPartidas() {
        viewModelScope.launch {
            try {
                useCases.obtenerPartidas().collectLatest { partidas ->
                    _uiState.update { it.copy(partidas = partidas) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            }
        }
    }

    fun findPartida(partidaId: Int) {
        viewModelScope.launch {
            if (partidaId > 0) {
                try {
                    val partida = useCases.obtenerPartida(partidaId)
                    if (partida != null) {
                        _uiState.update {
                            it.copy(
                                id = partida.id,
                                fecha = partida.fecha,
                                jugador1 = partida.jugador1,
                                jugador2 = partida.jugador2,
                                ganadorId = partida.ganadorId,
                                esFinalizada = partida.esFinalizada
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
                }
            }
        }
    }


    suspend fun savePartida(): Boolean {
        val currentState = _uiState.value
        val fecha = if (currentState.id == null || currentState.id == 0) {
            LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        } else {
            currentState.fecha
        }
        val partida = Partida(
            id = currentState.id,
            fecha = fecha,
            jugador1 = currentState.jugador1,
            jugador2 = currentState.jugador2,
            ganadorId = currentState.ganadorId,
            esFinalizada = currentState.esFinalizada
        )
        return try {
            val result = useCases.guardarPartida(partida)

            if (result.isFailure) {
                _uiState.update { it.copy(errorMessage = result.exceptionOrNull()?.message ?: "Error desconocido") }
                false
            } else {
                true
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            false
        }
    }


    private fun deletePartida() {
        viewModelScope.launch {
            try {
                val partida = Partida(
                    id = _uiState.value.id,
                    fecha = _uiState.value.fecha,
                    jugador1 = _uiState.value.jugador1,
                    jugador2 = _uiState.value.jugador2,
                    ganadorId = _uiState.value.ganadorId,
                    esFinalizada = _uiState.value.esFinalizada
                )
                useCases.eliminarPartida(partida)
                getPartidas()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            }
        }
    }


    private fun onPartidaChange(partidaId: Int){
        _uiState.update { it.copy(id = partidaId) }
    }

    private fun onFechaChange(fecha: String){
        _uiState.update { it.copy(fecha = fecha) }
    }

    private fun onJugador1Change(jugador1: Int){
        _uiState.update { it.copy(jugador1 = jugador1) }
    }

    private fun onJugador2Change(jugador2: Int){
        _uiState.update { it.copy(jugador2 = jugador2) }
    }

    private fun onGanadorChange(ganadorId: Int?){
        _uiState.update { it.copy(ganadorId = ganadorId) }
    }

    private fun onEsFinalizadaChange(esFinalizada: Boolean){
        _uiState.update { it.copy(esFinalizada = esFinalizada) }
    }



    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

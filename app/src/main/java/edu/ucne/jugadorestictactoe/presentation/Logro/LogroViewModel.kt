package edu.ucne.jugadorestictactoe.presentation.Logro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jugadorestictactoe.domain.model.Logro
import edu.ucne.jugadorestictactoe.domain.useCase.LogrosUseCase.LogrosUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LogroViewModel @Inject constructor(
    private val useCases: LogrosUseCases
): ViewModel() {
    private val _uiState = MutableStateFlow(LogroUiState.default())
    val uiState = _uiState.asStateFlow()

    init {
        getLogros()
    }

    fun onEvent(event: LogroEvent){
        when(event){
            is LogroEvent.nombreChange -> onNombreChange(event.nombre)
            is LogroEvent.logroChange -> onLogroChange(event.logroId)
            is LogroEvent.descripcionChange -> onDescripcionChange(event.descripcion)
            LogroEvent.delete -> deleteLogro()
            LogroEvent.new -> nuevo()
            LogroEvent.save -> viewModelScope.launch { saveLogro() }
        }
    }

    private fun nuevo() {
        _uiState.value = LogroUiState.default()
    }

    private fun getLogros() {
        viewModelScope.launch {
            try {
                useCases.obtenerLogros().collectLatest { logros ->
                    _uiState.update { it.copy(logros = logros) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            }
        }
    }

    fun findLogro(logroId: Int) {
        viewModelScope.launch {
            if (logroId > 0) {
                try {
                    val logro = useCases.obtenerLogro(logroId)
                    _uiState.update {
                        it.copy(
                            logroId = logro?.id,
                            nombre = logro?.nombre.orEmpty(),
                            descripcion = logro?.descripcion.orEmpty()
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
                }
            }
        }
    }

    suspend fun saveLogro(): Boolean {
        val currentState = _uiState.value
        val logro = Logro(
            id = currentState.logroId,
            nombre = currentState.nombre,
            descripcion = currentState.descripcion
        )
        return try {
            useCases.guardarLogro(logro)
            true
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            false
        }
    }


    private fun deleteLogro() {
        viewModelScope.launch {
            try {
                val logro = Logro(
                    id = _uiState.value.logroId,
                    nombre = _uiState.value.nombre,
                    descripcion = _uiState.value.descripcion
                )
                useCases.eliminarLogro(logro)
                getLogros()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "Error desconocido") }
            }
        }
    }

    private fun onDescripcionChange(descripcion: String){
        _uiState.update { it.copy(descripcion = descripcion) }
    }

    private fun onNombreChange(nombre: String){
        _uiState.update { it.copy(nombre = nombre) }
    }

    private fun onLogroChange(logroId: Int){
        _uiState.update { it.copy(logroId = logroId) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

package edu.ucne.jugadorestictactoe.presentation.Tecnico

import edu.ucne.jugadorestictactoe.domain.useCase.TecnicoUseCase.TecnicoUseCases


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.jugadorestictactoe.domain.model.Tecnico
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TecnicoViewModel @Inject constructor(
    private val useCases: TecnicoUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(TecnicoUiState.default())
    private val DEFAULT_ERROR_MESSAGE = "Error desconocido"
    val uiState = _uiState.asStateFlow()

    init {
        getTecnicos()
    }

    fun onEvent(event: TecnicoEvent){
        when(event){
            is TecnicoEvent.nombreChange -> onNombreChange(event.nombre)
            is TecnicoEvent.salarioHoraChange -> onSalarioHoraChange(event.salarioHora)
            is TecnicoEvent.tecnicoIdChange -> onTecnicoIdChange(event.tecnicoId)
            TecnicoEvent.save -> viewModelScope.launch { saveTecnico() }
            TecnicoEvent.delete -> deleteTecnico()
            TecnicoEvent.new -> nuevo()
        }
    }

    private fun nuevo() {
        _uiState.value = TecnicoUiState.default()
    }

    private fun getTecnicos() {
        viewModelScope.launch {
            try {
                useCases.obtenerTecnicos().collectLatest { lista ->
                    _uiState.update { it.copy(tecnicos = lista) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: DEFAULT_ERROR_MESSAGE) }            }
        }
    }


    fun findTecnico(tecnicoId: Int) {
        viewModelScope.launch {
            if (tecnicoId > 0) {
                try {
                    val tecnico = useCases.obtenerTecnico(tecnicoId)
                    _uiState.update {
                        it.copy(
                            tecnicoId = tecnico?.tecnicoId,
                            nombre = tecnico?.nombres.orEmpty(),
                            salarioHora = tecnico?.salarioHora
                        )
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = e.message ?: DEFAULT_ERROR_MESSAGE) }                }
            }
        }
    }

    suspend fun saveTecnico(): Boolean {
        val currentState = _uiState.value
        val tecnico = Tecnico(
            tecnicoId = currentState.tecnicoId ?: 0,
            nombres = currentState.nombre,
            salarioHora = currentState.salarioHora ?: 0.0
        )
        return try {
            useCases.guardarTecnico(tecnico)
            getTecnicos()
            true
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message ?: DEFAULT_ERROR_MESSAGE) }
            false
        }
    }

    private fun deleteTecnico() {
        viewModelScope.launch {
            try {
                val tecnico = Tecnico(
                    tecnicoId = _uiState.value.tecnicoId ?: 0,
                    nombres = _uiState.value.nombre,
                    salarioHora = _uiState.value.salarioHora ?: 0.0
                )
                useCases.eliminarTecnico(tecnico.tecnicoId)
                getTecnicos()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: DEFAULT_ERROR_MESSAGE) }            }
        }
    }

    private fun onNombreChange(nombre: String){
        _uiState.update { it.copy(nombre = nombre) }
    }

    private fun onSalarioHoraChange(salario: Double){
        _uiState.update { it.copy(salarioHora = salario) }
    }

    private fun onTecnicoIdChange(tecnicoId: Int?){
        _uiState.update { it.copy(tecnicoId = tecnicoId) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

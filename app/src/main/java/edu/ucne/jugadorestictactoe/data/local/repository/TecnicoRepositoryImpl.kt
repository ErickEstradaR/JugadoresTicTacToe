package edu.ucne.jugadorestictactoe.data.local.repository

import edu.ucne.jugadorestictactoe.data.remote.dto.Tecnico.TecnicoApi
import edu.ucne.jugadorestictactoe.data.remote.dto.Tecnico.TecnicosApiService
import edu.ucne.jugadorestictactoe.domain.model.Tecnico
import edu.ucne.jugadorestictactoe.domain.repository.TecnicoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class TecnicoRepositoryImpl @Inject constructor(
    private val api: TecnicosApiService
) : TecnicoRepository {

    override suspend fun getAllTecnicos(): List<Tecnico> {
        return api.getTecnicos().map { it.toDomain() }
    }

    override suspend fun getTecnicoById(id: Int): Tecnico? {
        val tecnicoApi = try {
            api.getTecnico(id)
        } catch (e: Exception) {
            null
        }
        return tecnicoApi?.toDomain()
    }

    override suspend fun createTecnico(tecnico: Tecnico) {
        api.createTecnico(tecnico.toApi())
    }

    override suspend fun updateTecnico(tecnico: Tecnico) {
        api.updateTecnico(tecnico.tecnicoId, tecnico.toApi())
    }

    override suspend fun deleteTecnico(id: Int?) {
        api.deleteTecnico(id)
    }

    override fun getAllFlow(): Flow<List<Tecnico>> = flow {
        try {
            val tecnicosApi = api.getTecnicos()
            emit(tecnicosApi.map { it.toDomain() })
        } catch (e: Exception) {
            emit(emptyList())
        }
    }


    private fun TecnicoApi.toDomain() = Tecnico(
        tecnicoId = this.tecnicoId,
        nombres = this.nombres,
        salarioHora = this.salarioHora
    )

    private fun Tecnico.toApi() = TecnicoApi(
        tecnicoId = this.tecnicoId,
        nombres = this.nombres,
        salarioHora = this.salarioHora
    )
}

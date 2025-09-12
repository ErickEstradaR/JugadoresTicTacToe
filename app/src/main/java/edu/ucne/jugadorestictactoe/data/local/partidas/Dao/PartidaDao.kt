package edu.ucne.jugadorestictactoe.data.local.partidas.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jugadorestictactoe.data.local.jugadores.Entities.JugadorEntity
import edu.ucne.jugadorestictactoe.data.local.partidas.Entity.PartidaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDao{
    @Upsert
    suspend fun savePartida(partidaEntity: PartidaEntity)

    @Query("""
            SELECT *
            FROM partidas
            WHERE partidaId =:id
            Limit 1
    """)
    suspend fun findPartida(id: Int): PartidaEntity?

    @Delete
    suspend fun deletePartida(partidaEntity: PartidaEntity)

    @Query("SELECT * FROM Partidas")
    fun getAllPartidas(): Flow<List<PartidaEntity>>
}
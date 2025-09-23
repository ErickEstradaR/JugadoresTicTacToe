package edu.ucne.jugadorestictactoe.data.local.logros

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jugadorestictactoe.data.local.partidas.Entity.PartidaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LogroDao{
    @Upsert
    suspend fun saveLogro(logroEntity: LogroEntity)

    @Query("""
            SELECT *
            FROM logros
            WHERE logroId =:id
            Limit 1
    """)
    suspend fun findLogro(id: Int): LogroEntity?

    @Delete
    suspend fun deletePartida(logroEntity: LogroEntity)

    @Query("SELECT * FROM Logros")
    fun getAllLogross(): Flow<List<LogroEntity>>
}
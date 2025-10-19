package edu.ucne.jugadorestictactoe.data.local.logros.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jugadorestictactoe.data.local.logros.entity.LogroEntity
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
    suspend fun deleteLogro(logroEntity: LogroEntity)

    @Query("SELECT * FROM Logros")
    fun getAllLogros(): Flow<List<LogroEntity>>
}
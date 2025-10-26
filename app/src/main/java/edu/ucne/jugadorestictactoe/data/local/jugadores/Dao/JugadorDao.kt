package edu.ucne.jugadorestictactoe.data.local.jugadores.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.jugadorestictactoe.data.local.jugadores.Entities.JugadorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JugadorDao{
    @Upsert
    suspend fun upsert(jugador: JugadorEntity)

    @Query(
        """
            SELECT *
            FROM Jugadores
            WHERE remoteId =:id
            Limit 1
    """
    )
    suspend fun find(id: String): JugadorEntity?

    @Query("DELETE FROM Jugadores WHERE jugadorId = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM Jugadores")
    fun observeJugadores(): Flow<List<JugadorEntity>>

    @Query("SELECT * FROM Jugadores WHERE isPendingCreate = 1")
    suspend fun getPendingCreateJugadores(): List<JugadorEntity>
}
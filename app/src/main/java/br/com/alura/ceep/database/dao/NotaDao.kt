package br.com.alura.ceep.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.alura.ceep.model.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {

    @Insert(onConflict = REPLACE)
    suspend fun salva(note: Nota)

    @Insert(onConflict = REPLACE)
    suspend fun salva(notas: List<Nota>)

    @Query("SELECT * FROM Nota WHERE disabled = 0")
    fun buscaTodas() : Flow<List<Nota>>

    @Query("SELECT * FROM Nota WHERE id = :id AND disabled = 0")
    fun buscaPorId(id: String): Flow<Nota>

    @Query("DELETE FROM Nota WHERE id = :id")
    suspend fun remove(id: String)

    @Query("SELECT * FROM Nota WHERE synchronized = 0 AND disabled = 0")
    fun buscaNaoSincronizadas() : Flow<List<Nota>>

    @Query("UPDATE Nota SET disabled = 1 WHERE id = :id")
    suspend fun desativa(id: String)

    @Query("SELECT * FROM Nota WHERE disabled = 1")
    fun buscaNotasDesativadas(): Flow<List<Nota>>


}
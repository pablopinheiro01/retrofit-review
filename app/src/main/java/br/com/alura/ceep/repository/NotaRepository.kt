package br.com.alura.ceep.repository

import br.com.alura.ceep.database.dao.NotaDao
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.NotaWebClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NotaRepository(
    private val dao: NotaDao,
    private val webClient: NotaWebClient
){

    fun buscaTodas(): Flow<List<Nota>> = dao.buscaTodas()

    private suspend fun atualizaTodas(){
        webClient.buscaTodas()?.let{ notas ->
            val notasSincronizada = notas.map { nota ->
                nota.copy(synchronized = true)
            }
            dao.salva(notasSincronizada)
        }
    }

    fun buscaPorId(id: String): Flow<Nota> {
        return dao.buscaPorId(id)
    }

    suspend fun remove(nota: String) {
        dao.remove(nota)
    }

    suspend fun salva(nota: Nota) {
        dao.salva(nota)
        if(webClient.salva(nota)){
            val notaSincronizada = nota.copy(synchronized = true)
            dao.salva(notaSincronizada)
        }
    }

    suspend fun sincronizaTodasAsNotas(){
        val notasNaoSincronizadas = dao.buscaNaoSincronizadas().first()
        notasNaoSincronizadas.forEach{ notaNaoSincronizada ->
            salva(notaNaoSincronizada)
        }
        atualizaTodas()
    }


}
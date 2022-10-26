package br.com.alura.ceep.webclient

import android.util.Log
import androidx.lifecycle.lifecycleScope
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.model.NotaRequest
import br.com.alura.ceep.webclient.model.NotaResponse
import br.com.alura.ceep.webclient.service.NotaService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "NotaWebClient"
class NotaWebClient {

    private val notaService: NotaService = RetrofitInitializer().notaService

    suspend fun buscaTodas(): List<Nota>?{

        return try {
                                                                                                                            notaService.buscaTodasCoroutines().map { nota ->
                nota.nota
            }
        } catch (e: Exception) {
            Log.e(TAG, "erro: $e")
            null
        }
    }

    suspend fun salva(nota: Nota): Boolean{
        try{
            val req = notaService.salva(
                nota.id,
                NotaRequest(
                    titulo = nota.titulo,
                    imagem = nota.imagem,
                    descricao = nota.descricao
                )
            )
//            if(req.isSuccessful){
//                Log.i(TAG, "Sucesso BB")
//            }else{
//                Log.i(TAG, "Tem parada errada ai irmao ${req.code()}")
//            }

            return req.isSuccessful

        }catch (e: Exception){
            Log.e(TAG, "Erro gerado: $e")
        }

        return false
    }

}
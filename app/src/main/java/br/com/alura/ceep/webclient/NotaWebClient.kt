package br.com.alura.ceep.webclient

import android.util.Log
import androidx.lifecycle.lifecycleScope
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.model.NotaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotaWebClient {

    suspend fun buscaTodas(): List<Nota> {

        return RetrofitInitializer().notaService.buscaTodasCoroutines().map { nota ->
            nota.nota
        }
    }

}
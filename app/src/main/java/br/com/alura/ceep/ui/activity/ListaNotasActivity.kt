package br.com.alura.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.alura.ceep.database.AppDatabase
import br.com.alura.ceep.databinding.ActivityListaNotasBinding
import br.com.alura.ceep.extensions.vaiPara
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.repository.NotaRepository
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter
import br.com.alura.ceep.webclient.NotaWebClient
import br.com.alura.ceep.webclient.RetrofitInitializer
import br.com.alura.ceep.webclient.model.NotaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaNotasActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityListaNotasBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaNotasAdapter(this)
    }
    private val repository by lazy{
        NotaRepository(AppDatabase.instancia(this).notaDao(), NotaWebClient())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraFab()
        configuraRecyclerView()
        lifecycleScope.launch {
            launch {
                atualizaTodasNotas()
            }
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                buscaNotas()
            }
        }

    }

    private suspend fun atualizaTodasNotas() {
        repository.atualizaTodas()
    }

    private fun configuraFab() {
        binding.activityListaNotasFab.setOnClickListener {
            Intent(this, FormNotaActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun configuraRecyclerView() {
        binding.activityListaNotasRecyclerview.adapter = adapter
        adapter.quandoClicaNoItem = { nota ->
            vaiPara(FormNotaActivity::class.java) {
                putExtra(NOTA_ID, nota.id)
            }
        }
    }

    private suspend fun buscaNotas() {
        repository.buscaTodas()
            .collect { notasEncontradas ->
                binding.activityListaNotasMensagemSemNotas.visibility =
                    if (notasEncontradas.isEmpty()) {
                        binding.activityListaNotasRecyclerview.visibility = GONE
                        VISIBLE
                    } else {
                        binding.activityListaNotasRecyclerview.visibility = VISIBLE
                        adapter.atualiza(notasEncontradas)
                        GONE
                    }
            }
    }


    private fun testeChamadaSuspendFunComCoroutinesComResponse() {
        lifecycleScope.launch {
            val listaResposta = RetrofitInitializer().notaService.buscaTodasCoroutinesResponse()

            val notas = listaResposta.body()?.map { response ->
                response.nota
            }

            Log.i("ListaNotas", "onCreate: $notas")

        }
    }

    private fun testeChamadaSuspendFunComCoroutines() {
        lifecycleScope.launch {
            val listaResposta = RetrofitInitializer().notaService.buscaTodasCoroutines()

            val notas = listaResposta.map { nota ->
                nota.nota
            }

            Log.i("ListaNotas", "onCreate: $notas")

        }
    }

    private fun testeChamadaComExecuteUsandoCoroutines(){

        val call: Call<List<NotaResponse>> = RetrofitInitializer().notaService.buscaTodos()

        lifecycleScope.launch(Dispatchers.IO) {

            val response: Response<List<NotaResponse>> = call.execute() //executa na main thread
            response.body()?.let{ notasResposta ->
                val notas: List<Nota> = notasResposta.map {
                    it.nota
                }
                Log.i("ListaNotas", "onCreate: $notas")
            }
        }
    }

    private fun testeChamadaAsyncComEnqueue(){

        val call: Call<List<NotaResponse>> = RetrofitInitializer().notaService.buscaTodos()

        call.enqueue(object : Callback<List<NotaResponse>?> {
            override fun onResponse(
                call: Call<List<NotaResponse>?>,
                response: Response<List<NotaResponse>?>
            ) {
                response.body()?.let{ notasResposta ->
                    val notas: List<Nota> = notasResposta.map {
                        it.nota
                    }
                    Log.i("ListaNotas", "onCreate: $notas")
                }
            }

            override fun onFailure(call: Call<List<NotaResponse>?>, t: Throwable) {
                Log.i("ListaNotas", "erro: $t")

            }
        })
    }
}
package br.com.alura.ceep.webclient.service

import br.com.alura.ceep.webclient.model.NotaResponse
import retrofit2.Call
import retrofit2.http.GET

interface NotaService {

    @GET("notas")
    fun buscaTodos(): Call<List<NotaResponse>>

}
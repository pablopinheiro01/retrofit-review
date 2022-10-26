package br.com.alura.ceep.webclient.service

import br.com.alura.ceep.webclient.model.NotaRequest
import br.com.alura.ceep.webclient.model.NotaResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface NotaService {

    @GET("notas")
    fun buscaTodos(): Call<List<NotaResponse>>

    @GET("notas")
    suspend fun buscaTodasCoroutines(): List<NotaResponse>

    @GET("notas")
    suspend fun buscaTodasCoroutinesResponse():Response<List<NotaResponse>>

    @PUT("notas/{uuid}")
    suspend fun salva(@Path("uuid")id:String, @Body nota: NotaRequest): Response<NotaResponse>

    @DELETE("notas/{uuid}")
    suspend fun remove(@Path("uuid")id: String): Response<Void>

}
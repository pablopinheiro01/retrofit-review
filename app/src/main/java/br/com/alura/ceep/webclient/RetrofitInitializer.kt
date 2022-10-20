package br.com.alura.ceep.webclient

import br.com.alura.ceep.webclient.service.NotaService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInitializer {

    private var retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.15.90:8080/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val notaService = retrofit.create(NotaService::class.java)
}
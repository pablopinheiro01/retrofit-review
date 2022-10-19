package br.com.alura.ceep.webclient

import br.com.alura.ceep.webclient.service.NotaService
import retrofit2.Retrofit

class RetrofitInitializer {

    private var retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8080/")
//        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val notaService = retrofit.create(NotaService::class.java)
}
package br.com.alura.ceep.webclient.model

import br.com.alura.ceep.model.Nota

class NotaResponse(
    val id: String?,
    val titulo: String?,
    val descricao: String? ,
    val imagem: String?
) {

    val nota: Nota get() = Nota(
        id = id ?: throw Exception("Ja existe o id"),
        titulo = titulo ?: "",
        descricao = descricao ?: "",
        imagem = imagem ?: ""
    )

}
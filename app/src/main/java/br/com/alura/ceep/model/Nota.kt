package br.com.alura.ceep.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Nota(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val descricao: String,
    val imagem: String? = null,

    //setado os objetos existentes na base com o valor default de false
    // o tipo Bool nao e suportado pelo sqlite por isso o uso de Strings
    //0 = false e 1 = true
    @ColumnInfo(defaultValue = "0")
    val synchronized: Boolean = false
)

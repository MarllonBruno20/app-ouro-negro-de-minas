package br.com.marllon.cafeouronegrodeminas.model

data class Endereco(
    val cep: String = "",
    val logradouro: String = "",
    val bairro: String = "",
    val numero: Int = 0,
    val cidade: String = "",
    val uf: String = ""
)

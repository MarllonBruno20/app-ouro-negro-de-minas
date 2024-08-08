package br.com.marllon.cafeouronegrodeminas.model

data class Cliente (
    val cpf: String ="",
    val nome: String = "",
    val telefone: String= "",
    val endereco: Endereco = Endereco(),
    val email: String = "",
    val instagram: String = "",
    val situacao: Situacao = Situacao.ATIVO
)
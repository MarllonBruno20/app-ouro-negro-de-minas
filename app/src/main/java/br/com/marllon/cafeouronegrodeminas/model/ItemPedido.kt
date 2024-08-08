package br.com.marllon.cafeouronegrodeminas.model

data class ItemPedido(
    val idItemPedido: String = "",
    val idPedido: String = "",
    val idProduto: String = "",
    var quantidade: Int = 0,
    val valorUnitario: Double = 0.0
)

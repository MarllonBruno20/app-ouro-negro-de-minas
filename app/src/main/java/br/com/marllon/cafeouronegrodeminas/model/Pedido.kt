package br.com.marllon.cafeouronegrodeminas.model

import java.util.Date

data class Pedido(
    val idPedido: String = "",
    val data: Date = Date(),
    val cpfCliente: String = "",
    var itens: List<ItemPedido> = emptyList()
) {
    val valorTotal: Double
        get() = itens.sumOf { it.quantidade * it.valorUnitario }
}

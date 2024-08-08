package br.com.marllon.cafeouronegrodeminas.dao

import android.util.Log
import br.com.marllon.cafeouronegrodeminas.model.ItemPedido
import br.com.marllon.cafeouronegrodeminas.model.Pedido
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class PedidoDao {

    private val db = Firebase.firestore

    fun inserePedido(pedido: Pedido) {
        val pedidoRef = db.collection("pedidos").document()
        val idPedido = pedidoRef.id

        val pedidoComId = pedido.copy(idPedido = idPedido)

        pedidoRef.set(pedidoComId)
            .addOnSuccessListener {
                for (item in pedidoComId.itens) {
                    val itemPedidoRef = db.collection("itens_pedido").document()
                    val idItemPedido = itemPedidoRef.id
                    val itemComId = item.copy(idItemPedido = idItemPedido, idPedido = idPedido)
                    itemPedidoRef.set(itemComId)
                }
            }
            .addOnFailureListener { e ->
                Log.w("PedidoDao", "Erro ao inserir pedido", e)
            }
    }

    suspend fun recuperaPedidos(): List<Pedido> = coroutineScope {
        try {
            val pedidosResult = db.collection("pedidos").get().await()
            val pedidos = pedidosResult.toObjects(Pedido::class.java)

            for (pedido in pedidos) {
                val itensResult = db.collection("itens_pedido")
                    .whereEqualTo("idPedido", pedido.idPedido)
                    .get()
                    .await()
                val itens = itensResult.toObjects(ItemPedido::class.java)
                pedido.itens = itens
            }

            pedidos
        } catch (e: Exception) {
            Log.w("PedidoDao", "Erro ao recuperar pedidos: ", e)
            emptyList()
        }
    }

    fun atualiza(pedido: Pedido, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("pedidos").document(pedido.idPedido)
            .set(pedido)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
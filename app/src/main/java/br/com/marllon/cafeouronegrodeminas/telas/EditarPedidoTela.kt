package br.com.marllon.cafeouronegrodeminas.telas

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.marllon.cafeouronegrodeminas.dao.PedidoDao
import br.com.marllon.cafeouronegrodeminas.model.ItemPedido
import br.com.marllon.cafeouronegrodeminas.model.Pedido

@Composable
fun EditarPedidoTela(navController: NavHostController, idPedido: String) {
    val pedidoDao = PedidoDao()
    var pedido by remember { mutableStateOf<Pedido?>(null) }
    var itensPedidos by remember { mutableStateOf(listOf<ItemPedido>()) }

    LaunchedEffect(idPedido) {
        val pedidos = pedidoDao.recuperaPedidos()
        val pedidoEncontrado = pedidos.find { it.idPedido == idPedido }
        pedido = pedidoEncontrado

        pedidoEncontrado?.let {
            itensPedidos = it.itens
        }
    }

    pedido?.let {
        Column(modifier = Modifier
            .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "Editar Pedido",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyColumn {
                items(itensPedidos) { itemPedido ->
                    QuantidadeRow(
                        itemPedido = itemPedido,
                        onQuantidadeAlterada = { novaQuantidade ->
                            itemPedido.quantidade = novaQuantidade
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val pedidoEditado = it.copy(
                        itens = itensPedidos
                    )
                    pedidoDao.atualiza(pedidoEditado,
                        onSuccess = {
                            navController.popBackStack()
                        },
                        onFailure = { e ->
                            Log.e("EditarPedidoTela", "Erro ao atualizar pedido", e)
                        }
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Salvar Alterações")
            }
        }
    } ?: run {
        Text(text = "Carregando...", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun QuantidadeRow(
    itemPedido: ItemPedido,
    onQuantidadeAlterada: (Int) -> Unit
) {
    var quantidade by remember { mutableStateOf(itemPedido.quantidade) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = itemPedido.idProduto,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        TextField(
            value = quantidade.toString(),
            onValueChange = { novaQuantidade ->
                quantidade = novaQuantidade.toIntOrNull() ?: quantidade
                onQuantidadeAlterada(quantidade)
            },
            label = { Text("Quantidade") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(100.dp)
        )
    }
}

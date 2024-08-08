package br.com.marllon.cafeouronegrodeminas.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.marllon.cafeouronegrodeminas.model.ItemPedido
import br.com.marllon.cafeouronegrodeminas.model.Pedido
import br.com.marllon.cafeouronegrodeminas.ui.theme.corPrincipal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PedidoCard(
    pedido: Pedido,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val formatacaoData = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = corPrincipal
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clickable {
                        navController.navigate("editar_pedido/${pedido.idPedido}")
                    }
            ) {
                Text(
                    text = "ID Pedido: ${pedido.idPedido}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Data: ${formatacaoData.format(pedido.data)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "CPF do Cliente: ${pedido.cpfCliente}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Itens:",
                    style = MaterialTheme.typography.titleMedium
                )
                pedido.itens.forEach { item ->
                    Text(
                        text = "Produto: ${item.idProduto}, " +
                                "Quantidade: ${item.quantidade}, " +
                                "Valor Unitário: R\$ ${item.valorUnitario}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Valor Total: R$ ${pedido.valorTotal}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PedidoCardPreview() {
    val navController = rememberNavController()

    val pedidoExemplo = Pedido(
        idPedido = "12345",
        data = Date(),
        cpfCliente = "123.456.789-00",
        itens = listOf(
            ItemPedido(
                idItemPedido = "1", idPedido = "12345",
                idProduto = "produto1", quantidade = 2, valorUnitario = 10.0
            ),
            ItemPedido(
                idItemPedido = "2", idPedido = "12345",
                idProduto = "produto2", quantidade = 1, valorUnitario = 20.0
            )
        )
    )

    PedidoCard(
        pedido = pedidoExemplo,
        navController = navController
    )
}
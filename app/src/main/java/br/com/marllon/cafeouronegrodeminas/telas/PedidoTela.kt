package br.com.marllon.cafeouronegrodeminas.telas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.marllon.cafeouronegrodeminas.componentes.BarraPesquisa
import br.com.marllon.cafeouronegrodeminas.componentes.FloatinActionButton
import br.com.marllon.cafeouronegrodeminas.componentes.PedidoCard
import br.com.marllon.cafeouronegrodeminas.dao.PedidoDao
import br.com.marllon.cafeouronegrodeminas.model.Pedido

@Composable
fun PedidoTela(navController: NavHostController) {
    val pedidoDao = PedidoDao()
    var pedidos by remember { mutableStateOf(emptyList<Pedido>()) }

    LaunchedEffect(Unit) {
        pedidos = pedidoDao.recuperaPedidos()
    }

    var query by remember { mutableStateOf("") }

    val filtroProdutos = pedidos.filter {
        it.idPedido.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            BarraPesquisa(query = query,
                placeholder = "Digite o ID do pedido",
                onQueryChange = { query = it }
            )
        },
        floatingActionButton = {
            FloatinActionButton(
                onClick = {
                    navController.navigate("cadastro_pedido")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            items(filtroProdutos) { pedido ->
                PedidoCard(
                    pedido = pedido,
                    navController = navController,
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable {
                            navController.navigate("editar_pedido/${pedido.idPedido}")
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PedidoTelaPreview() {
    val navController = rememberNavController()
    PedidoTela(navController = navController)
}
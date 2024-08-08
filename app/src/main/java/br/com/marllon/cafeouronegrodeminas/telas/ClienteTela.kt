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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.marllon.cafeouronegrodeminas.componentes.BarraPesquisa
import br.com.marllon.cafeouronegrodeminas.componentes.ClienteCard
import br.com.marllon.cafeouronegrodeminas.componentes.FloatinActionButton
import br.com.marllon.cafeouronegrodeminas.dao.ClienteDao
import br.com.marllon.cafeouronegrodeminas.model.Cliente

@Composable
fun ClienteTela(navController: NavHostController) {

    val clienteDao = ClienteDao()
    var clientes by remember { mutableStateOf(listOf<Cliente>()) }

    LaunchedEffect(Unit) {
        clientes = clienteDao.recuperaTodos()
    }

    var query by remember { mutableStateOf("") }

    val filtroClientes = clientes.filter {
        it.nome.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            BarraPesquisa(query = query,
                placeholder = "Digite o nome do cliente",
                onQueryChange = { query = it }
            )
        },
        floatingActionButton = {
            FloatinActionButton(
                onClick = {
                    navController.navigate("cadastro_cliente")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
        ) {
            items(filtroClientes) { cliente ->
                ClienteCard(
                    cliente = cliente,
                    navController = navController,
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable {
                            navController.navigate("editar_cliente/${cliente.cpf}")
                        }
                )
            }
        }
    }
}

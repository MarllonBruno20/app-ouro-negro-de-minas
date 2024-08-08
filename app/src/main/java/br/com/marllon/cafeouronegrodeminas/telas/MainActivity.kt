package br.com.marllon.cafeouronegrodeminas.telas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.marllon.cafeouronegrodeminas.dao.ClienteDao
import br.com.marllon.cafeouronegrodeminas.dao.PedidoDao
import br.com.marllon.cafeouronegrodeminas.dao.ProdutoDao
import br.com.marllon.cafeouronegrodeminas.ui.theme.CafeOuroNegroDeMinasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CafeOuroNegroDeMinasTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colorScheme.background) {
                    val items = remember {
                        listOf(
                            Pair("Clientes", Icons.Filled.Home),
                            Pair("Produtos", Icons.Filled.Shop),
                            Pair("Pedidos", Icons.Filled.ShoppingCart),
                        )
                    }

                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    var itemSelecionado by remember {
                        mutableStateOf(items.first())
                    }

                    Column(Modifier.fillMaxSize()) {
                        NavHost(
                            navController = navController,
                            startDestination = "clientes",
                            Modifier.weight(1f)
                        ) {
                            composable("clientes") {
                                ClienteTela(navController = navController)
                            }
                            composable("produtos") {
                                ProdutoTela(navController = navController)
                            }
                            composable("pedidos") {
                                PedidoTela(navController = navController)
                            }
                            composable("cadastro_cliente") {
                                CadastroClienteTela(onSave = { cliente ->
                                    ClienteDao().insere(cliente)
                                    navController.popBackStack()
                                })
                            }
                            composable(
                                route = "editar_cliente/{cpf}",
                                arguments = listOf(navArgument("cpf") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val cpf = backStackEntry.arguments?.getString("cpf")
                                cpf?.let {
                                    EditarClienteTela(it, navController)
                                }
                            }
                            composable("cadastro_produto") {
                                CadastroProdutoTela(onSave = { produto ->
                                    ProdutoDao().insere(produto)
                                    navController.popBackStack()
                                })
                            }
                            composable("cadastro_pedido") {
                                CadastroPedidoTela(onSave = { pedido ->
                                    PedidoDao().inserePedido(pedido)
                                    navController.popBackStack()
                                })
                            }
                            composable(
                                route = "editar_pedido/{idPedido}",
                                arguments = listOf(navArgument("idPedido") {
                                    type = NavType.StringType
                                })
                            ) { backStackEntry ->
                                val idPedido = backStackEntry.arguments?.getString("idPedido") ?: ""
                                EditarPedidoTela(navController = navController, idPedido = idPedido)
                            }
                        }
                        BottomAppBar(actions = {
                            items.forEach { item ->
                                val texto = item.first
                                val icone = item.second
                                val selected = currentRoute == texto.lowercase()

                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        itemSelecionado = item
                                        val rota = when (texto) {
                                            "Clientes" -> "clientes"
                                            "Produtos" -> "produtos"
                                            else -> "pedidos"
                                        }
                                        navController.navigate(rota) {
                                            launchSingleTop = true
                                        }
                                    },
                                    icon = {
                                        Icon(icone, contentDescription = null)
                                    },
                                    label = { Text(texto) }
                                )
                            }
                        })
                    }
                }
            }
        }
    }
}

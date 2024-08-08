package br.com.marllon.cafeouronegrodeminas.telas

import android.util.Log
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
import br.com.marllon.cafeouronegrodeminas.componentes.ProdutoCard
import br.com.marllon.cafeouronegrodeminas.dao.ProdutoDao
import br.com.marllon.cafeouronegrodeminas.model.Produto

@Composable
fun ProdutoTela(navController: NavHostController) {
    val produtoDao = ProdutoDao()
    var produtos by remember { mutableStateOf(listOf<Produto>()) }

    LaunchedEffect(Unit) {
        try {
            produtos = produtoDao.recuperaTodos()
            Log.d("ProdutoTela", "Produtos recuperados: ${produtos.size}")
        } catch (e: Exception) {
            Log.w("ProdutoTela", "Erro ao recuperar produtos: ", e)
        }
    }

    var query by remember { mutableStateOf("") }

    val filtroProdutos = produtos.filter {
        it.idProduto.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            BarraPesquisa(query = query,
                placeholder = "Digite o ID do produto",
                onQueryChange = { query = it }
            )
        },
        floatingActionButton = {
            FloatinActionButton(
                onClick = {
                    navController.navigate("cadastro_produto")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    )  {paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            items(filtroProdutos) { produto ->
                ProdutoCard(
                    produto = produto,
                    navController = navController,
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable {
                            navController.navigate("editar_cliente/${produto.idProduto}")
                        }

                )
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun ProdutoTelaPreview() {
    val navController = rememberNavController()
    ProdutoTela(navController)
}
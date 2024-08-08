package br.com.marllon.cafeouronegrodeminas.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import br.com.marllon.cafeouronegrodeminas.model.PontoTorra
import br.com.marllon.cafeouronegrodeminas.model.Produto
import br.com.marllon.cafeouronegrodeminas.model.TipoGrao
import br.com.marllon.cafeouronegrodeminas.ui.theme.corPrincipal

@Composable
fun ProdutoCard(
    produto: Produto,
    navController: NavController,
    modifier: Modifier = Modifier
) {
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
                        navController.navigate("detalhes_produto/${produto.idProduto}")
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Id do produto: ${produto.idProduto}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tipo do grão: ${produto.tipoGrao.descricao}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Ponto da torra: ${produto.pontoTorra.descricao}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Valor: ${produto.valor}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Blend: ${if (produto.blend) "Sim" else "Não"}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProdutoCardPreview() {
    val navController = rememberNavController()

    val cafe = Produto(
        idProduto = "01",
        tipoGrao = TipoGrao.ARABICA_DO_CERRADO,
        pontoTorra = PontoTorra.FORTE,
        valor = 150.0,
        blend = true
    )

    ProdutoCard(
        produto = cafe,
        navController = navController
    )

}
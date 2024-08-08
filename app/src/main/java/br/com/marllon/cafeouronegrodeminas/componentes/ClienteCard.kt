package br.com.marllon.cafeouronegrodeminas.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.marllon.cafeouronegrodeminas.model.Cliente
import br.com.marllon.cafeouronegrodeminas.model.Endereco
import br.com.marllon.cafeouronegrodeminas.model.Situacao
import br.com.marllon.cafeouronegrodeminas.ui.theme.corPrincipal


@Composable
fun ClienteCard(cliente: Cliente,
                navController: NavController,
                modifier: Modifier = Modifier) {
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
                          .clickable{
                              navController.navigate("editar_cliente/${cliente.cpf}")
                          }
                  ) {
                      Row(
                          modifier = Modifier
                              .fillMaxWidth()
                      ) {
                          Text(text = "Nome: ${cliente.nome}",
                              style = MaterialTheme.typography.titleMedium,
                              fontWeight = FontWeight.Bold
                          )
                      }

                      Text(
                          text = "CPF: ${cliente.cpf}",
                          style = MaterialTheme.typography.bodyMedium,

                      )
                      Text(
                          text = "Endereço: ${cliente.endereco.cep} " +
                                  "\n${cliente.endereco.logradouro} - ${cliente.endereco.numero}" +
                                  "\n${cliente.endereco.bairro}" +
                                  "\n${cliente.endereco.cidade} - ${cliente.endereco.uf}",
                          style = MaterialTheme.typography.bodyMedium
                      )
                      Text(
                          text = "Telefone: ${cliente.telefone}",
                          style = MaterialTheme.typography.bodyMedium
                      )
                      Text(
                          text = "Instagram: ${cliente.instagram}",
                          style = MaterialTheme.typography.bodyMedium
                      )
                      Text(
                          text = "Situação: ${cliente.situacao}",
                          style = MaterialTheme.typography.bodyMedium,
                          color = when (cliente.situacao) {
                              Situacao.ATIVO -> Color.Green
                              Situacao.INATIVO -> Color.Red
                              Situacao.SUSPENSO -> Color.Yellow
                              else -> Color.Black
                          }
                      )
                  }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewClienteCard() {
    val navController = rememberNavController()
    val cliente = Cliente(
        cpf = "123.456.789-10",
        nome = "João Silva",
        telefone = "99999-9999",
        endereco = Endereco(
            cep = "12345-678",
            logradouro = "Rua Exemplo, 123",
            bairro = "Centro",
            numero = 123,
            cidade = "Cidade Exemplo",
            uf = "EX"
        ),
        instagram = "@joaosilva",
        situacao = Situacao.ATIVO
    )

    ClienteCard(cliente = cliente, navController = navController)
}
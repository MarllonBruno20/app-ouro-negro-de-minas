package br.com.marllon.cafeouronegrodeminas.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.marllon.cafeouronegrodeminas.componentes.DefaultTextFieldCliente
import br.com.marllon.cafeouronegrodeminas.componentes.DropDown
import br.com.marllon.cafeouronegrodeminas.dao.ClienteDao
import br.com.marllon.cafeouronegrodeminas.dao.ProdutoDao
import br.com.marllon.cafeouronegrodeminas.model.ItemPedido
import br.com.marllon.cafeouronegrodeminas.model.Pedido
import br.com.marllon.cafeouronegrodeminas.model.PontoTorra
import br.com.marllon.cafeouronegrodeminas.model.Produto
import br.com.marllon.cafeouronegrodeminas.model.TipoGrao
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun CadastroPedidoTela(
    onSave: (Pedido) -> Unit,
    produtoDao: ProdutoDao = ProdutoDao(),
    clienteDao: ClienteDao = ClienteDao()
) {
    var clienteCpf by remember { mutableStateOf("") }
    val currentDate = remember { Date() }
    var produtoSelecionado by remember { mutableStateOf<Produto?>(null) }
    var quantidade by remember { mutableStateOf("") }
    var itensPedido by remember { mutableStateOf(listOf<ItemPedido>()) }
    var produtos by remember { mutableStateOf(listOf<Produto>()) }
    var cpfs by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        produtos = produtoDao.recuperaTodos()
        cpfs = clienteDao.recuperaCPFs()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp)
    ) {
        // Dropdown CPF do Cliente
        DropDown(
            items = cpfs,
            label = "CPF do Cliente",
            selectedItem = clienteCpf,
            onItemSelected = { clienteCpf = it },
            modifier = Modifier.fillMaxWidth(),
            itemLabel = { it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar Data Atual
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        TextField(
            value = dateFormat.format(currentDate),
            onValueChange = { /* Nada a fazer */ },
            label = { Text("Data do Pedido") },
            readOnly = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null)
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown Produto
        DropDown(
            items = produtos,
            label = "Produto",
            selectedItem = produtoSelecionado ?: Produto(
                "",
                tipoGrao = TipoGrao.ARABICA_DO_CERRADO,
                pontoTorra = PontoTorra.MEDIA,
                valor = 0.0,
                blend = false
            ),
            onItemSelected = { produtoSelecionado = it },
            modifier = Modifier.fillMaxWidth(),
            itemLabel = { it.idProduto }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo Quantidade
        DefaultTextFieldCliente(
            value = quantidade,
            onValueChange = { quantidade = it },
            label = "Quantidade",
            placeholder = "Digite a quantidade",
            leadingIcon = Icons.Default.ShoppingCart,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão Adicionar Produto
        Button(
            onClick = {
                produtoSelecionado?.let {
                    val itemPedido = ItemPedido(
                        idItemPedido = UUID.randomUUID().toString(),
                        idPedido = "", // Será atualizado ao salvar o pedido
                        idProduto = it.idProduto,
                        quantidade = quantidade.toInt(),
                        valorUnitario = it.valor
                    )
                    itensPedido = itensPedido + itemPedido
                    produtoSelecionado = null
                    quantidade = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Adicionar Produto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de Itens do Pedido
        LazyColumn {
            items(itensPedido) { item ->
                Text(
                    text = "Produto: ${item.idProduto}, Quantidade: ${item.quantidade}, Valor Unitário: R$ ${item.valorUnitario}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão Salvar Pedido
        Button(
            onClick = {
                val pedido = Pedido(
                    idPedido = UUID.randomUUID().toString(),
                    data = currentDate,
                    cpfCliente = clienteCpf,
                    itens = itensPedido
                )
                onSave(pedido)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Pedido")
        }
    }
}

package br.com.marllon.cafeouronegrodeminas.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.marllon.cafeouronegrodeminas.componentes.DropDown
import br.com.marllon.cafeouronegrodeminas.model.PontoTorra
import br.com.marllon.cafeouronegrodeminas.model.Produto
import br.com.marllon.cafeouronegrodeminas.model.TipoGrao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroProdutoTela(onSave: (Produto) -> Unit) {
    var tipoGrao by remember { mutableStateOf(TipoGrao.ARABICA_DO_CERRADO) }
    var pontoTorra by remember { mutableStateOf(PontoTorra.MEDIA) }
    var valor by remember { mutableStateOf("") }
    var blend by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Cadastro de Produto") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DropDown(
                    items = TipoGrao.values().toList(),
                    label = "Tipo de Grão",
                    selectedItem = tipoGrao,
                    onItemSelected = { selected ->
                        tipoGrao = selected
                    },
                    itemLabel = { it.descricao }
                )

                DropDown(
                    items = PontoTorra.values().toList(),
                    label = "Ponto de Torra",
                    selectedItem = pontoTorra,
                    onItemSelected = { selected ->
                        pontoTorra = selected
                    },
                    itemLabel = { it.descricao }
                )

                OutlinedTextField(
                    value = valor,
                    onValueChange = { valor = it },
                    label = { Text("Valor") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Blend")
                    Switch(
                        checked = blend,
                        onCheckedChange = { blend = it }
                    )
                }

                Button(
                    onClick = {
                        val produto = Produto(
                            idProduto = "",
                            tipoGrao = tipoGrao,
                            pontoTorra = pontoTorra,
                            valor = valor.toDoubleOrNull() ?: 0.0,
                            blend = blend
                        )
                        onSave(produto)
                    }
                ) {
                    Text("Salvar Produto")
                }
            }
        }
    )
}


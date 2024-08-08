package br.com.marllon.cafeouronegrodeminas.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.marllon.cafeouronegrodeminas.componentes.DefaultTextFieldCliente
import br.com.marllon.cafeouronegrodeminas.model.Cliente
import br.com.marllon.cafeouronegrodeminas.model.Endereco

@Composable
fun CadastroClienteTela(
    onSave: (Cliente) -> Unit,
    modifier: Modifier = Modifier
) {
    var cpf by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }

    var cep by remember { mutableStateOf("") }
    var logradouro by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cadastro de Cliente",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        DefaultTextFieldCliente(
            value = cpf,
            onValueChange = { cpf = it },
            label = "CPF",
            enabled = false,
            placeholder = "Digite o CPF",
            leadingIcon = Icons.Default.AccountBox,
            singleLine = true,
            charLimit = 11,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        DefaultTextFieldCliente(
            value = nome,
            onValueChange = { nome = it },
            label = "Nome",
            placeholder = "Digite o nome",
            leadingIcon = Icons.Default.Person,
            singleLine = true,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        DefaultTextFieldCliente(
            value = telefone,
            onValueChange = { telefone = it },
            label = "Telefone",
            placeholder = "Digite o telefone",
            leadingIcon = Icons.Default.Phone,
            singleLine = true,
            charLimit = 11,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        DefaultTextFieldCliente(
            value = instagram,
            onValueChange = { instagram = it },
            label = "Instagram",
            placeholder = "Digite o Instagram",
            leadingIcon = Icons.Default.AccountCircle,
            singleLine = true,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        DefaultTextFieldCliente(
            value = cep,
            onValueChange = { cep = it },
            label = "CEP",
            placeholder = "Digite o CEP",
            leadingIcon = Icons.Default.Numbers,
            singleLine = true,
            charLimit = 8,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        DefaultTextFieldCliente(
            value = logradouro,
            onValueChange = { logradouro = it },
            label = "Logradouro",
            placeholder = "Digite o Logradouro",
            leadingIcon = Icons.Default.Abc,
            singleLine = true,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        DefaultTextFieldCliente(
            value = bairro,
            onValueChange = { bairro = it },
            label = "Bairro",
            placeholder = "Digite o Bairro",
            leadingIcon = Icons.Default.Abc,
            singleLine = true,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        DefaultTextFieldCliente(
            value = numero,
            onValueChange = { numero = it },
            label = "Número",
            placeholder = "Digite o Número",
            leadingIcon = Icons.Default.Numbers,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        DefaultTextFieldCliente(
            value = cidade,
            onValueChange = { cidade = it },
            label = "Cidade",
            placeholder = "Digite a Cidade",
            leadingIcon = Icons.Default.Abc,
            singleLine = true,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        DefaultTextFieldCliente(
            value = uf,
            onValueChange = { uf = it },
            label = "UF",
            placeholder = "Digite a UF",
            leadingIcon = Icons.Default.Abc,
            singleLine = true,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val endereco = Endereco(
                    cep = cep,
                    logradouro = logradouro,
                    bairro = bairro,
                    numero = numero.toIntOrNull() ?: 0,
                    cidade = cidade,
                    uf = uf
                )
                val cliente = Cliente(
                    cpf = cpf,
                    nome = nome,
                    telefone = telefone,
                    endereco = endereco,
                    instagram = instagram
                )
                onSave(cliente)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Cadastrar Cliente")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CadastroClienteTelaPreview() {
    CadastroClienteTela(onSave = {})
}
package br.com.marllon.cafeouronegrodeminas.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraPesquisa(
    modifier: Modifier = Modifier,
    query: String = "",
    placeholder: String = "",
    onQueryChange: (String) -> Unit = {}
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    SearchBar(
        modifier = modifier,
        //valor sendo digitado
        query = query,
        //função que atualiza o valor digitado
        onQueryChange = {
            onQueryChange(it)
        },
        //quando pesquisa
        onSearch = {

        },
        active = false,
        //quando o campo de pesquisa fica ativo
        onActiveChange = {
        },
        placeholder = {
            Text(
                text = placeholder,
                color = Color.Gray
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Lupa para pesquisa.",
                tint = Color.Gray
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Lupa para pesquisa.",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onQueryChange("")
                        keyboardController?.hide()
                    },
                tint = Color.Gray
            )
        },
        content = {

        })
}

@Preview(showBackground = true)
@Composable
fun BarraPesquisaPreview() {

    var query by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BarraPesquisa(
            query = query,
            placeholder = "Digite o nome do cliente",
            onQueryChange = {
            }
        )
    }
}
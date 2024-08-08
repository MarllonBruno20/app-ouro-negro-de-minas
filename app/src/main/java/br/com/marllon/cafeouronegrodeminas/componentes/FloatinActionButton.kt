package br.com.marllon.cafeouronegrodeminas.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FloatinActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() }
    ) {
        Icon(Icons.Filled.Add, "Botão para adicionar novo cliente.")
    }
}

@Preview
@Composable
fun FloatinActionButtonPreview() {
    FloatinActionButton(onClick = { /*TODO*/ })
}
package br.com.marllon.cafeouronegrodeminas.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DefaultTextFieldCliente(
    modifier: Modifier = Modifier,
    value: String,
    enabled : Boolean = true,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    singleLine: Boolean = true,
    charLimit: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= charLimit) {
                onValueChange(it)
            }
        },
        modifier = modifier,
        enabled = true,
        label = {
            label?.let {
                Text(text = it)
            }
        },
        placeholder = {
            placeholder?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        color = Color.Gray
                    )
                )
            }
        },
        leadingIcon = {
            leadingIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            if(value.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onValueChange("")
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    })
            }
        },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultTextFieldClientePreview() {
    var cpf by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultTextFieldCliente(
            value = cpf,
            onValueChange = { cpf = it },
            label = "CPF",
            enabled = true,
            placeholder = "XXX.XXX.XXX-XX",
            leadingIcon = Icons.Default.AccountCircle,
            singleLine = true,
            charLimit = 11,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

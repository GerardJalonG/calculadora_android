package com.example.calculadora

import android.R.attr.enabled
import android.R.attr.type
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(modifier: Modifier = Modifier) {

    var num1 by remember { mutableStateOf("") }
    var num2 by remember { mutableStateOf("") }

    var resultado by remember { mutableStateOf("") }


    val operations = listOf("+", "-", "*", "/")
    var expanded by remember { mutableStateOf(false) }
    var selectedOperation by remember { mutableStateOf(operations[0]) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Calculadora",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(bottom = 24.dp) )
        Row (modifier = Modifier.padding(16.dp)) {
            TextField(
                value = num1,
                onValueChange = { nuevo ->
                    num1 = nuevo.filter { it.isDigit() }
                },
                label = { Text("Primer numero") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = num2,
                onValueChange = { nuevo ->
                    num2 = nuevo.filter { it.isDigit() }
                },
                label = { Text("Segundo numero") },
                modifier = Modifier.weight(1f)
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = "Operación: $selectedOperation",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    operations.forEach { operation ->
                        DropdownMenuItem(
                            text = { Text(operation) },
                            onClick = {
                                selectedOperation = operation
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Button(onClick = {
            if (num1.isNotEmpty() || num2.isNotEmpty()) {
                val numero1 = num1.toDouble()
                val numero2 = num2.toDouble()

                if(numero2 == 0.0 && selectedOperation == "/"){
                    val errorMessage = "No se puede dividir entre 0"
                    resultado = errorMessage
                    return@Button
                }
                var operation = when (selectedOperation) {
                    "+" -> numero1 + numero2
                    "-" -> numero1 - numero2
                    "*" -> numero1 * numero2
                    "/" -> numero1 / numero2
                    else -> 0
                }

                resultado = operation.toString()

            } else {
                resultado = "Introduce ambos números"
            }
        } ,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Calcular")
        }
        if (resultado.isNotEmpty()) {
            Text(text = "Resultado: $resultado")

        }
    }
}


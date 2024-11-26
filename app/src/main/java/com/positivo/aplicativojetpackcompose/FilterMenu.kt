package com.positivo.aplicativojetpackcompose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterMenu(
    filters: List<String>,
    onFilterSelected: (String) -> Unit
) {
    Row(modifier = Modifier.padding(8.dp)) {
        filters.forEach { filter ->
            Button(
                onClick = { onFilterSelected(filter) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = filter)
            }
        }
    }
}
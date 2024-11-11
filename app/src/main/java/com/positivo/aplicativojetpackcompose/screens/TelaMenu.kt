package com.positivo.aplicativojetpackcompose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.positivo.aplicativojetpackcompose.R
import com.positivo.aplicativojetpackcompose.ui.theme.AplicativoJetpackComposeTheme

@Composable
fun TelaMenu(navController: NavController) {
    AplicativoJetpackComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(10.dp),
            shadowElevation = 10.dp,
            border = BorderStroke(0.1.dp, Color.LightGray),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Ícone centralizado
                Image(
                    painter = painterResource(id = R.drawable.catalogo), // Substitua pelo seu ícone
                    contentDescription = "Ícone",
                    modifier = Modifier.size(120.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botão de Cadastro
                Button(
                    onClick = {
                        // Navegar para a tela de cadastro (exemplo)
                        navController.navigate("cadastro")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                ) {
                    Text(text = "Cadastrar", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botão de Login
                Button(
                    onClick = {
                        // Navegar para a tela de login (exemplo)
                        navController.navigate("login")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF6200EE))
                ) {
                    Text(text = "Logar", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaMenuPreview() {
    TelaMenu(navController = rememberNavController())
}

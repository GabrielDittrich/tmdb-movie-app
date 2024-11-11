package com.positivo.aplicativojetpackcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.positivo.aplicativojetpackcompose.ui.theme.AplicativoJetpackComposeTheme

@Composable
fun TelaLogin(navController: NavController) {
    AplicativoJetpackComposeTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Login", style = MaterialTheme.typography.headlineLarge)

                Spacer(modifier = Modifier.height(32.dp))

                val (email, setEmail) = remember { mutableStateOf("") }
                val (senha, setSenha) = remember { mutableStateOf("") }

                // Campo de Email
                OutlinedTextField(
                    value = email,
                    onValueChange = setEmail,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de Senha
                OutlinedTextField(
                    value = senha,
                    onValueChange = setSenha,
                    label = { Text("Senha") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation() // Máscara de senha
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botão de Login
                Button(
                    onClick = {
                        // Lógica de login aqui
                        // Exemplo: navController.navigate("home")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logar", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Voltar para a tela de cadastro
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Ainda não tem conta? Cadastre-se")
                }
            }
        }
    }
}
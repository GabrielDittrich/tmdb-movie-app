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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.positivo.aplicativojetpackcompose.ui.theme.AplicativoJetpackComposeTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TelaCadastro(navController: NavController) {
    val auth = FirebaseAuth.getInstance()  // Instância do FirebaseAuth

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Cadastro", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Senha
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Exibe mensagem de erro, se houver
        if (erro.isNotEmpty()) {
            Text(text = erro, color = Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Botão de Cadastro
        Button(
            onClick = {
                if (email.isNotEmpty() && senha.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Cadastro bem-sucedido
                                navController.popBackStack()  // Volta para a tela de login
                            } else {
                                // Mostra o erro
                                erro = task.exception?.localizedMessage ?: "Erro desconhecido"
                            }
                        }
                } else {
                    erro = "Por favor, preencha todos os campos."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cadastrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Voltar para a tela de login
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Já tem conta? Faça Login")
        }
    }
}

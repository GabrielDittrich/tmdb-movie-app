    package com.positivo.aplicativojetpackcompose

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.viewModels
    import androidx.activity.enableEdgeToEdge
    import androidx.navigation.compose.rememberNavController
    import com.google.firebase.FirebaseApp
    import com.google.firebase.auth.FirebaseAuth

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            FirebaseApp.initializeApp(this)
            enableEdgeToEdge()

            val auth = FirebaseAuth.getInstance()

            setContent {
                // Inicialize o NavController no contexto correto
                val navController = rememberNavController()

                // Passe o navController para a função de navegação
                FilmeNavigation(navController = navController, auth = auth)
            }
        }
    }

    package com.positivo.aplicativojetpackcompose

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.navigation.compose.rememberNavController
    import com.app.myapplication.HomeNavigation
    import com.google.firebase.Firebase
    import com.google.firebase.FirebaseApp
    import com.google.firebase.auth.auth
    import com.positivo.aplicativojetpackcompose.screens.principal.HomeScreen
    import com.positivo.aplicativojetpackcompose.ui.theme.AplicativoJetpackComposeTheme

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            FirebaseApp.initializeApp(this)
            enableEdgeToEdge()

            val auth = Firebase.auth
            val currentUser = auth.currentUser

            setContent {
                if (currentUser != null) {
                    HomeNavigation()
                } else {
                    FilmeNavigation()
                }
            }
        }
    }
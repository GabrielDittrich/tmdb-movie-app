package com.positivo.aplicativojetpackcompose.screens.principal

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

@Composable
fun PerfilScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()

    var nome by remember { mutableStateOf(TextFieldValue("")) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf("") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> imageUri = uri }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (imageUrl.isNotEmpty()) {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Foto de Perfil",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        } else {
            Text("Selecione uma foto de perfil")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("Selecionar Imagem")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (imageUri != null) {
                    uploadProfileData(imageUri!!, nome.text, storage, db) { uploadedUrl ->
                        imageUrl = uploadedUrl
                    }
                }
            }
        ) {
            Text("Salvar Perfil")
        }
    }
}

fun uploadProfileData(
    uri: Uri,
    nome: String,
    storage: FirebaseStorage,
    db: FirebaseFirestore,
    onUploadSuccess: (String) -> Unit
) {
    val storageRef = storage.reference
    val fileName = UUID.randomUUID().toString()
    val imageRef = storageRef.child("images/$fileName")

    imageRef.putFile(uri)
        .addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                val profileData = hashMapOf(
                    "nome" to nome,
                    "imageUrl" to downloadUri.toString()
                )
                db.collection("users")
                    .document("profile")
                    .set(profileData)
                    .addOnSuccessListener {
                        Log.d("Firebase", "Perfil salvo com sucesso!")
                        onUploadSuccess(downloadUri.toString())
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firebase", "Erro ao salvar perfil", e)
                    }
            }
        }
        .addOnFailureListener { e ->
            Log.e("Firebase", "Erro ao fazer upload da imagem", e)
        }
}

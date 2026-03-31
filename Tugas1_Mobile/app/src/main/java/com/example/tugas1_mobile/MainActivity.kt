package com.example.tugas1_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas1_mobile.ui.theme.Tugas1_MobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AplikasiSapaan()
        }
    }
}

@Composable
fun AplikasiSapaan() {
    var isDarkTheme by remember { mutableStateOf(false) }
    var nama by remember { mutableStateOf("") }
    var hasilSapaan by remember { mutableStateOf("") }

    Tugas1_MobileTheme(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Aplikasi Sapaan",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Masukkan nama Anda") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    hasilSapaan = if (nama.isNotEmpty()) {
                        "Hello $nama"
                    } else {
                        "Hello"
                    }
                }) {
                    Text("Sapa", fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = hasilSapaan,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Tema Gelap", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { isDarkTheme = it }
                    )
                }
            }
        }
    }
}
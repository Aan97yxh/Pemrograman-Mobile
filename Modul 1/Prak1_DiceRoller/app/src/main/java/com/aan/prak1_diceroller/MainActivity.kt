package com.aan.prak1_diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerApp()
                }
            }
        }
    }
}

@Composable
fun DiceRollerApp() {
    // State untuk menyimpan angka dadu dan pesan hasil
    var resultLeft by remember { mutableIntStateOf(0) }
    var resultRight by remember { mutableIntStateOf(0) }
    var messageResult by remember { mutableStateOf("") }

    // Pemetaan gambar dadu [cite: 469-470]
    val imageResourceLeft = when (resultLeft) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_0
    }

    val imageResourceRight = when (resultRight) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_0
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Konten utama di tengah (Dadu & Tombol)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Image(
                    painter = painterResource(imageResourceLeft),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(imageResourceRight),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                resultLeft = (1..6).random()
                resultRight = (1..6).random()

                messageResult = if (resultLeft == resultRight) {
                    "Selamat, anda dapat dadu double!"
                } else {
                    "Anda belum beruntung!"
                }
            }) {
                Text(text = "ROLL")
            }
        }

        // Pesan hasil di paling bawah
        if (messageResult.isNotEmpty()) {
            val bgColor = if (resultLeft == resultRight) Color(0xFFD1C4E9) else Color(0xFFF5F5F5)
            Text(
                text = messageResult,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(bgColor)
                    .padding(16.dp)
            )
        }
    }
}
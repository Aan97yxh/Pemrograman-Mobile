package com.example.prak3_scrollable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.prak3_scrollable.ui.navigation.GenshinNavGraph
import com.example.prak3_scrollable.ui.theme.GenshinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GenshinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = GenshinTheme.colors.background
                ) {
                    GenshinNavGraph()
                }
            }
        }
    }
}

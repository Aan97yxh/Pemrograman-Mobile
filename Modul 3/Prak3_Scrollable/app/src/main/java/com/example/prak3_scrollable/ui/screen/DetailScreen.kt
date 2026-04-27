package com.example.prak3_scrollable.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prak3_scrollable.model.Character
import com.example.prak3_scrollable.ui.theme.GenshinColors
import com.example.prak3_scrollable.ui.theme.GenshinTheme

@Composable
fun DetailScreen(character: Character, onBackClick: () -> Unit) {
    val colors = GenshinTheme.colors
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize().background(colors.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // ── Hero Image ──
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
            ) {
                Image(
                    painter = painterResource(id = character.imageRes),
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // ── Detail Content ──
            Column(modifier = Modifier.padding(20.dp)) {

                // Name + Element Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = character.name,
                        color = colors.textPrimary,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = GenshinColors.Primary.copy(alpha = 0.15f),
                        modifier = Modifier
                    ) {
                        Text(
                            text = character.element,
                            color = colors.accent,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Divider
                HorizontalDivider(color = colors.divider, thickness = 1.dp)

                Spacer(modifier = Modifier.height(16.dp))

                // Weapon + Rarity Row
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Weapon
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Weapon",
                            color = colors.textSecondary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = character.weaponType,
                            color = colors.textPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    // Rarity
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Rarity",
                            color = colors.textSecondary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "★".repeat(character.rarity),
                            color = colors.rarityGold,
                            fontSize = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Divider
                HorizontalDivider(color = colors.divider, thickness = 1.dp)

                Spacer(modifier = Modifier.height(16.dp))

                // About
                Text(
                    text = "About",
                    color = colors.textPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = character.descriptionDetail,
                    color = colors.textSecondary,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
            }
        }

        // ── Back Button (pinned top-left) ──
        SmallFloatingActionButton(
            onClick = onBackClick,
            containerColor = colors.card,
            contentColor = colors.textPrimary,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
    }
}

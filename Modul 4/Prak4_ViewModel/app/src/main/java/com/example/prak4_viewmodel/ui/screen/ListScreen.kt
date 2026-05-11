package com.example.prak4_viewmodel.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.prak4_viewmodel.model.Character
import com.example.prak4_viewmodel.ui.theme.GenshinTheme
import com.example.prak4_viewmodel.viewmodel.CharacterViewModel

@Composable
fun ListScreen(
    viewModel: CharacterViewModel,
    onDetailClick: (Character) -> Unit
) {
    val colors = GenshinTheme.colors

    // Observe StateFlow dari ViewModel
    val characters by viewModel.characters.collectAsStateWithLifecycle()

    val verticalListState = rememberLazyListState()
    val horizontalListState = rememberLazyListState()

    LazyColumn(
        state = verticalListState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // ── Featured Section (LazyRow) ──
        item {
            Text(
                text = "Featured Characters",
                color = colors.textPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
            )
        }

        item {
            LazyRow(
                state = horizontalListState,
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                items(characters) { character ->
                    FeaturedCard(
                        character = character,
                        onClick = {
                            viewModel.onDetailClicked(character)
                            onDetailClick(character)
                        }
                    )
                }
            }
        }

        // ── All Characters Section ──
        item {
            Text(
                text = "All Characters",
                color = colors.textPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    start = 16.dp, end = 16.dp,
                    top = 24.dp, bottom = 12.dp
                )
            )
        }

        items(characters) { character ->
            CharacterCard(
                character = character,
                onYoutubeClick = { viewModel.onYoutubeClicked(character) },
                onDetailClick = {
                    viewModel.onDetailClicked(character)
                    onDetailClick(character)
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// ── Featured Card ──
@Composable
fun FeaturedCard(character: Character, onClick: () -> Unit) {
    val colors = GenshinTheme.colors

    Card(
        modifier = Modifier
            .width(140.dp)
            .fillMaxHeight()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.card),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = character.imageRes),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = character.name,
                    color = colors.textPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = character.element,
                    color = colors.accent,
                    fontSize = 11.sp
                )
            }
        }
    }
}

// ── Character Card ──
@Composable
fun CharacterCard(
    character: Character,
    onYoutubeClick: () -> Unit,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = GenshinTheme.colors
    val context = LocalContext.current

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.card),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = character.imageRes),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Row 1: Name | Element
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = character.name,
                        color = colors.textPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = character.element,
                        color = colors.accent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Row 2: Weapon | Rarity
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = character.weaponType,
                        color = colors.textSecondary,
                        fontSize = 13.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "★".repeat(character.rarity),
                        color = colors.rarityGold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = character.description,
                    color = colors.textSecondary,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Button(
                        onClick = {
                            onYoutubeClick()
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(character.youtubeUrl))
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(text = "YouTube", fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = onDetailClick,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(text = "Detail", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

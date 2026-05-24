package com.example.prak5_internet.feature.movie.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.prak5_internet.core.common.UiState
import com.example.prak5_internet.ui.theme.MovieTheme
import com.example.prak5_internet.feature.movie.presentation.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int,
    viewModel: MovieViewModel,
    onBackClick: () -> Unit
) {
    // Panggil skema warna kustom estetikamu
    val colors = MovieTheme.colors
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val baseImageUrl = "https://image.tmdb.org/t/p/w500"

    LaunchedEffect(movieId) {
        viewModel.loadMovieDetail(movieId)
    }

    Box(modifier = Modifier.fillMaxSize().background(colors.background)) {
        when (val state = detailState) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = colors.primary
                )
            }

            is UiState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { viewModel.loadMovieDetail(movieId) },
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                    ) {
                        Text("Retry", color = colors.textPrimary)
                    }
                }
            }

// ... potongan kode atas tetap sama ...
            is UiState.Success -> {
                val movie = state.data

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
                        AsyncImage(
                            model = baseImageUrl + movie.posterPath,
                            contentDescription = movie.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // ── Detail Content ──
                    Column(modifier = Modifier.padding(20.dp)) {

                        // Row 1: Title + Rating Tag Atas
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = movie.title,
                                color = colors.textPrimary,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = colors.primary.copy(alpha = 0.15f),
                                modifier = Modifier
                            ) {
                                Text(
                                    text = "⭐ ${"%.1f".format(movie.voteAverage)}",
                                    color = colors.accent,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Divider 1
                        HorizontalDivider(color = colors.divider, thickness = 1.dp)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Row 2: Info Utama Simetris (Release Date & Movie Rating - Murni Angka)
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Release Date",
                                    color = colors.textSecondary,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = movie.releaseDate,
                                    color = colors.textPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Movie Rating",
                                    color = colors.textSecondary,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "${"%.1f".format(movie.voteAverage)} / 10",
                                    color = colors.textPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Divider 2
                        HorizontalDivider(color = colors.divider, thickness = 1.dp)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Row 3: Meta Tambahan TMDb (Vote Count & Popularity)
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Vote Count",
                                    color = colors.textSecondary,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "${movie.voteCount} Users",
                                    color = colors.textPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Popularity Score",
                                    color = colors.textSecondary,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "${"%.0f".format(movie.popularity)} pts",
                                    color = colors.textPrimary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Divider 3
                        HorizontalDivider(color = colors.divider, thickness = 1.dp)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Bagian Sinopsis / Overview
                        Text(
                            text = "Overview",
                            color = colors.textPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = movie.overview.ifEmpty { "No overview available for this movie." },
                            color = colors.textSecondary,
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
        }

        // ── Back Button (Pinned Top-Left Mengambang Estetik Modul 4) ──
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
package com.example.prak5_internet.feature.movie.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.prak5_internet.core.common.UiState
import com.example.prak5_internet.ui.theme.MovieTheme
import com.example.prak5_internet.feature.movie.domain.model.Movie
import com.example.prak5_internet.feature.movie.presentation.components.FeaturedMovieCard
import com.example.prak5_internet.feature.movie.presentation.components.MovieListItem
import com.example.prak5_internet.feature.movie.presentation.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    viewModel: MovieViewModel,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onDetailClick: (Movie) -> Unit
) {
    val colors = MovieTheme.colors

    val moviesState by viewModel.moviesState.collectAsStateWithLifecycle()
    val lastOpenedTitle by viewModel.lastOpenedTitle.collectAsStateWithLifecycle()

    val verticalListState = rememberLazyListState()
    val horizontalListState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Popular Movies",
                            color = colors.textPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        lastOpenedTitle?.let {
                            Text(
                                text = "Last Opened: $it",
                                fontSize = 11.sp,
                                color = colors.textSecondary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colors.background),
                actions = {
                    IconButton(onClick = { viewModel.loadPopularMovies() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = colors.textPrimary)
                    }
                    IconButton(onClick = onToggleDarkMode) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle dark mode",
                            tint = colors.textPrimary
                        )
                    }
                }
            )
        },
        containerColor = colors.background
    ) { paddingValues ->

        when (val state = moviesState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = colors.primary)
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.loadPopularMovies() },
                            colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
                        ) {
                            Text("Retry", color = colors.textPrimary)
                        }
                    }
                }
            }

            is UiState.Success -> {
                val movies = state.data

                LazyColumn(
                    state = verticalListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    // ── 1. Featured Section ──
                    item {
                        Text(
                            text = "Featured Movies",
                            color = colors.textPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp)
                        )
                    }

                    item {
                        LazyRow(
                            state = horizontalListState,
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(210.dp)
                        ) {
                            items(movies.take(10)) { movie ->
                                FeaturedMovieCard(
                                    movie = movie,
                                    onClick = {
                                        viewModel.onMovieOpened(movie)
                                        onDetailClick(movie)
                                    }
                                )
                            }
                        }
                    }

                    // ── 2. All Movies Section ──
                    item {
                        Text(
                            text = "All Movies",
                            color = colors.textPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 12.dp)
                        )
                    }

                    items(movies) { movie ->
                        MovieListItem(
                            movie = movie,
                            onDetailClick = {
                                viewModel.onMovieOpened(movie)
                                onDetailClick(movie)
                            },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}
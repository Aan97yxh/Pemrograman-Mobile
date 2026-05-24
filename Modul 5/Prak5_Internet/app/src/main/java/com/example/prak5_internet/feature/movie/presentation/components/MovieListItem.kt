package com.example.prak5_internet.feature.movie.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.prak5_internet.ui.theme.MovieTheme
import com.example.prak5_internet.feature.movie.domain.model.Movie

// ── KOMPONEN KARTU HORIZONTAL FEATURED ──
@Composable
fun FeaturedMovieCard(
    movie: Movie,
    onClick: () -> Unit
) {
    val colors = MovieTheme.colors

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
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
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
                    text = movie.title,
                    color = colors.textPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = movie.releaseDate.take(4),
                    color = colors.accent,
                    fontSize = 11.sp
                )
            }
        }
    }
}

// ── KOMPONEN DAFTAR BARIS VERTIKAL UTAMA ──
@Composable
fun MovieListItem(
    movie: Movie,
    onDetailClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MovieTheme.colors
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
            AsyncImage(
                model = movie.posterUrl,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(145.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                // Baris 1: Judul | Tahun Rilis
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = movie.title,
                        color = colors.textPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = movie.releaseDate.take(4),
                        color = colors.accent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Baris 2: Tampilan Rating Bersih
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "User Rating",
                        color = colors.textSecondary,
                        fontSize = 13.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "⭐ ${"%.1f".format(movie.voteAverage)} / 10",
                        color = colors.accent,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sinopsis Pendek Film
                Text(
                    text = movie.overview,
                    color = colors.textSecondary,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Tombol Aksi
                Row {
                    Button(
                        onClick = {
                            timber.log.Timber.d("Tombol Trailer diklik untuk film: ${movie.title}")

                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.youtubeTrailerUrl))
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(text = "Trailer", fontSize = 12.sp, color = colors.textPrimary)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = onDetailClick,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(text = "Detail", fontSize = 12.sp, color = colors.textPrimary)
                    }
                }
            }
        }
    }
}
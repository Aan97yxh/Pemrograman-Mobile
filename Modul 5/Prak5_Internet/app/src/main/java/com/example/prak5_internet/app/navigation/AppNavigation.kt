package com.example.prak5_internet.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prak5_internet.core.database.AppDatabase
import com.example.prak5_internet.core.network.ApiClient
import com.example.prak5_internet.core.preferences.AppPreferences
import com.example.prak5_internet.feature.movie.data.remote.api.MovieApiService
import com.example.prak5_internet.feature.movie.data.repository.MoviePreferencesRepositoryImpl
import com.example.prak5_internet.feature.movie.data.repository.MovieRepositoryImpl
import com.example.prak5_internet.feature.movie.domain.usecase.GetLastOpenedMovieTitleUseCase
import com.example.prak5_internet.feature.movie.domain.usecase.GetMovieByIdUseCase
import com.example.prak5_internet.feature.movie.domain.usecase.GetPopularMoviesUseCase
import com.example.prak5_internet.feature.movie.domain.usecase.SaveLastOpenedMovieUseCase
import com.example.prak5_internet.feature.movie.presentation.screens.MovieDetailScreen
import com.example.prak5_internet.feature.movie.presentation.screens.MovieScreen
import com.example.prak5_internet.feature.movie.presentation.viewmodel.MovieViewModel
import com.example.prak5_internet.feature.movie.presentation.viewmodel.MovieViewModelFactory

object MovieRoutes {
    const val LIST = "movie_list"
    const val DETAIL = "movie_detail/{movieId}"
    fun detail(movieId: Int) = "movie_detail/$movieId"
}

@Composable
fun AppNavigation(appPreferences: AppPreferences) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val isDarkMode by appPreferences.isDarkMode.collectAsStateWithLifecycle()

    // Wiring dependencies
    val apiService = remember {
        ApiClient.retrofit.create(MovieApiService::class.java)
    }
    val dao = remember {
        AppDatabase.getInstance(context).movieDao()
    }
    val movieRepository = remember {
        MovieRepositoryImpl(apiService, dao)
    }
    val prefsRepository = remember {
        MoviePreferencesRepositoryImpl(appPreferences)
    }
    val factory = remember {
        MovieViewModelFactory(
            getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository),
            getMovieByIdUseCase = GetMovieByIdUseCase(movieRepository),
            saveLastOpenedMovieUseCase = SaveLastOpenedMovieUseCase(prefsRepository),
            getLastOpenedMovieTitleUseCase = GetLastOpenedMovieTitleUseCase(prefsRepository)
        )
    }

    val viewModel: MovieViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = MovieRoutes.LIST
    ) {
        composable(MovieRoutes.LIST) {
            MovieScreen(
                viewModel = viewModel,
                isDarkMode = isDarkMode,
                onToggleDarkMode = { appPreferences.setDarkMode(!isDarkMode) },
                onDetailClick = { movie ->
                    navController.navigate(MovieRoutes.detail(movie.id))
                }
            )
        }

        composable(MovieRoutes.DETAIL) { backStackEntry ->
            val movieId = backStackEntry.arguments
                ?.getString("movieId")
                ?.toIntOrNull() ?: return@composable
            MovieDetailScreen(
                movieId = movieId,
                viewModel = viewModel,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}
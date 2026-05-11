package com.example.prak4_viewmodel.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prak4_viewmodel.model.Character
import com.example.prak4_viewmodel.ui.screen.DetailScreen
import com.example.prak4_viewmodel.ui.screen.ListScreen
import com.example.prak4_viewmodel.viewmodel.CharacterViewModel
import com.example.prak4_viewmodel.viewmodel.CharacterViewModelFactory
import androidx.compose.ui.res.stringResource

// ── Route constants ──
object GenshinRoutes {
    const val LIST = "list"
    const val DETAIL = "detail"
}

@Composable
fun GenshinNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // ViewModel dibuat sekali di NavGraph agar shared antar screen
    val viewModel: CharacterViewModel = viewModel(
        factory = CharacterViewModelFactory(stringResource(
            com.example.prak4_viewmodel.R.string.app_name
        ))
    )

    NavHost(
        navController = navController,
        startDestination = GenshinRoutes.LIST
    ) {
        composable(GenshinRoutes.LIST) {
            ListScreen(
                viewModel = viewModel,
                onDetailClick = { character ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("character", character)
                    navController.navigate(GenshinRoutes.DETAIL)
                }
            )
        }

        composable(GenshinRoutes.DETAIL) {
            val character = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<Character>("character")

            character?.let {
                DetailScreen(
                    character = it,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}

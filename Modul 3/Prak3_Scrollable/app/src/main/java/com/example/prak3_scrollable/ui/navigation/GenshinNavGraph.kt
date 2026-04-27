package com.example.prak3_scrollable.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prak3_scrollable.model.Character
import com.example.prak3_scrollable.ui.screen.DetailScreen
import com.example.prak3_scrollable.ui.screen.ListScreen

// ── Route constants ──
object GenshinRoutes {
    const val LIST = "list"
    const val DETAIL = "detail"
}

@Composable
fun GenshinNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = GenshinRoutes.LIST
    ) {
        composable(GenshinRoutes.LIST) {
            ListScreen(
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

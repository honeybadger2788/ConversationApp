package com.girlify.conversationApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.girlify.conversationApp.categories.ui.categoriesScreen.CategoriesScreen
import com.girlify.conversationApp.categories.ui.questionScreen.QuestionScreen
import com.girlify.conversationApp.categories.ui.splashScreen.SplashViewModel
import com.girlify.conversationApp.model.Routes
import com.girlify.conversationApp.ui.theme.ConversationAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition{splashViewModel.isLoading.value}
        setContent {
            ConversationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MyNavigation()
                }
            }
        }
    }
}

@Composable
private fun MyNavigation() {
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = Routes.Categories.route
    ) {
        composable(Routes.Categories.route) {
            CategoriesScreen({
                navigationController.navigate(
                    Routes.Questions.createRoute(
                        categoryId = it
                    )
                )
            })
        }
        composable(
            Routes.Questions.route,
            arguments = listOf(navArgument("categoryId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            QuestionScreen(
                navigationController::popBackStack,
                backStackEntry.arguments?.getString("categoryId")?:""
            )
        }
    }
}
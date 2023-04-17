package com.girlify.conversationApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.girlify.conversationApp.categories.ui.categoriesScreen.CategoriesScreen
import com.girlify.conversationApp.categories.ui.questionScreen.QuestionScreen
import com.girlify.conversationApp.model.Routes
import com.girlify.conversationApp.ui.theme.ConversationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConversationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = Routes.Categories.route
                    ) {
                        composable(Routes.Categories.route) {
                            CategoriesScreen(navigationController)
                        }
                        composable(
                            Routes.Questions.route,
                            arguments = listOf(navArgument("categoryName") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            QuestionScreen(
                                navigationController,
                                backStackEntry.arguments?.getString("categoryName") ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.girlify.conversationApp.categories.ui.categoriesScreen.CategoriesScreen
import com.girlify.conversationApp.categories.ui.categoriesScreen.CategoriesViewModel
import com.girlify.conversationApp.categories.ui.questionScreen.QuestionScreen
import com.girlify.conversationApp.categories.ui.questionScreen.QuestionViewModel
import com.girlify.conversationApp.model.Routes
import com.girlify.conversationApp.ui.SplashScreen
import com.girlify.conversationApp.ui.theme.ConversationAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val categoriesViewModel: CategoriesViewModel by viewModels()
    private val questionViewModel: QuestionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConversationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MyNavigation(categoriesViewModel, questionViewModel)
                }
            }
        }
    }
}

@Composable
private fun MyNavigation(
    categoriesViewModel: CategoriesViewModel,
    questionViewModel: QuestionViewModel
) {
    val navigationController = rememberNavController()
    NavHost(
        navController = navigationController,
        startDestination = Routes.SplashScreen.route
    ) {
        composable(Routes.SplashScreen.route) {
            SplashScreen(navigationController)
        }
        composable(Routes.Categories.route) {
            CategoriesScreen(navigationController, categoriesViewModel)
        }
        composable(
            Routes.Questions.route,
            arguments = listOf(navArgument("categoryId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            QuestionScreen(
                navigationController,
                backStackEntry.arguments?.getString("categoryId")?:"",
                questionViewModel
            )
        }
    }
}
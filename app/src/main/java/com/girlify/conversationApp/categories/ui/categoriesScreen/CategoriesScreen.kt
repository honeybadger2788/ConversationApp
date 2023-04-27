package com.girlify.conversationApp.categories.ui.categoriesScreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import com.girlify.conversationApp.model.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val CATEGORIES_LIST_TEST_TAG = "categories_list_test_tag"
@Composable
fun CategoriesScreen(
    navigationController: NavHostController,
    categoriesViewModel: CategoriesViewModel
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    var doubleBackToExitPressedOnce by remember { mutableStateOf(false) }
    val backPressDispatcher =
        LocalOnBackPressedDispatcherOwner.current!!.onBackPressedDispatcher
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val uiState by produceState<CategoriesUiState>(
        initialValue = CategoriesUiState.Loading,
        key1 = lifecycle,
        key2 = categoriesViewModel
    ){
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            categoriesViewModel.uiState.collect{ value = it }
        }
    }

    when(uiState){
        is CategoriesUiState.Error -> {}
        CategoriesUiState.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is CategoriesUiState.Success -> {
            CategoriesList(
                (uiState as CategoriesUiState.Success).categories){
                navigationController.navigate(
                    Routes.Questions.createRoute(
                        categoryId = it.id
                    )
                )
            }
            BackHandler {
                if (doubleBackToExitPressedOnce) {
                    navigationController.popBackStack()
                    backPressDispatcher.onBackPressed()
                } else {
                    doubleBackToExitPressedOnce = true
                    Toast.makeText(
                        context,
                        "Presiona de nuevo para salir",
                        Toast.LENGTH_SHORT
                    ).show()
                    scope.launch {
                        delay(1000)
                        if (doubleBackToExitPressedOnce) {
                            doubleBackToExitPressedOnce = false
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        categoriesViewModel.getCategories()
    }
}

@Composable
fun CategoriesList(
    categories: List<CategoryModel>,
    goToQuestions: (CategoryModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .testTag(CATEGORIES_LIST_TEST_TAG)
    ) {
        items(categories) { category ->
            ItemCategory(
                category = category,
                modifier = Modifier
                    .fillMaxSize()
                    .height(170.dp)
            ) {
                goToQuestions(category)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemCategory(
    category: CategoryModel,
    modifier: Modifier,
    onItemSelected: (CategoryModel) -> Unit
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onItemSelected(category) },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            GlideImage(
                model = category.image,
                contentDescription = category.name,
                Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = category.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

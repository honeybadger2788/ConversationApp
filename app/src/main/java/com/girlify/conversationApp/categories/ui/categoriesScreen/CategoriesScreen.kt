package com.girlify.conversationApp.categories.ui.categoriesScreen

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import com.girlify.conversationApp.model.Routes


@Composable
fun CategoriesScreen(
    navigationController: NavHostController,
    categoriesViewModel: CategoriesViewModel
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

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
                CircularProgressIndicator()
            }
        }
        is CategoriesUiState.Success -> {
            CategoriesList(
                (uiState as CategoriesUiState.Success).categories,
                navigationController
            )
        }
    }

    LaunchedEffect(Unit) {
        categoriesViewModel.getCategories()
    }
}

@Composable
fun CategoriesList(
    categories: List<CategoryModel>,
    navigationController: NavHostController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        items(categories) { category ->
            ItemCategory(
                category = category,
                modifier = Modifier
                    .fillMaxSize()
                    .height(170.dp)
            ) {
                navigationController.navigate(
                    Routes.Questions.createRoute(
                        categoryId = category.id
                    )
                )
            }
        }
    }
}

//fun getCategories(): List<CategoryModel> {
//    return listOf(
//        CategoryModel("En el ascensor", icon = Icons.Default.Elevator),
//        CategoryModel("En la fila del super", icon = Icons.Default.ShoppingCart),
//        CategoryModel("En un velorio", icon = Icons.Default.SentimentVeryDissatisfied),
//        CategoryModel("En el proctologo", icon = Icons.Default.Medication),
//        CategoryModel("Reunión de consorcio", icon = Icons.Default.Apartment),
//        CategoryModel("Con amigos de tu pareja", icon = Icons.Default.Group),
//        CategoryModel("Reunion de padres", icon = Icons.Default.FamilyRestroom),
//        CategoryModel("Pagando el alquiler", icon = Icons.Default.Payments),
//        CategoryModel("En el gym", icon = Icons.Default.FitnessCenter),
//        CategoryModel("En el colectivo", icon = Icons.Default.DepartureBoard)
//    )
//}

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
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = "category icon",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = category.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

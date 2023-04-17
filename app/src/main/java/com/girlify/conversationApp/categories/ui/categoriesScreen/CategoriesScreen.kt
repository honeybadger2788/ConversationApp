package com.girlify.conversationApp.categories.ui.categoriesScreen

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.DepartureBoard
import androidx.compose.material.icons.filled.Elevator
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import com.girlify.conversationApp.model.Routes


@Composable
fun CategoriesScreen(
    navigationController: NavHostController,
    categoriesViewModel: CategoriesViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        items(getCategories()) { category ->
            ItemCategory(
                category = category,
                modifier = Modifier
                    .fillMaxSize()
                    .height(170.dp)
            ) {
                navigationController.navigate(Routes.Questions.createRoute(
                    categoryName = category.name
                ))
            }
        }
    }
}

fun getCategories(): List<CategoryModel> {
    return listOf(
        CategoryModel("En el ascensor", icon = Icons.Default.Elevator),
        CategoryModel("En la fila del super", icon = Icons.Default.ShoppingCart),
        CategoryModel("En un velorio", icon = Icons.Default.SentimentVeryDissatisfied),
        CategoryModel("En el proctologo", icon = Icons.Default.Medication),
        CategoryModel("Reunión de consorcio", icon = Icons.Default.Apartment),
        CategoryModel("Con amigos de tu pareja", icon = Icons.Default.Group),
        CategoryModel("Reunion de padres", icon = Icons.Default.FamilyRestroom),
        CategoryModel("Pagando el alquiler", icon = Icons.Default.Payments),
        CategoryModel("En el gym", icon = Icons.Default.FitnessCenter),
        CategoryModel("En el colectivo", icon = Icons.Default.DepartureBoard)
    )
}

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
                imageVector = category.icon,
                contentDescription = "category icon",
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = category.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

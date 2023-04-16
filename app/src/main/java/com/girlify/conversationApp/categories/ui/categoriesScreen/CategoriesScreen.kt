package com.girlify.conversationApp.categories.ui.categoriesScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.DepartureBoard
import androidx.compose.material.icons.filled.Elevator
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.outlined.Dangerous
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import com.girlify.conversationApp.model.Routes


@Composable
fun CategoriesScreen(navigationController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp)
    ) {
        items(getCategories()) { it ->
            ItemCategory(
                category = it,
                modifier = Modifier.fillMaxSize().height(220.dp)
            ) {
                navigationController.navigate(Routes.Questions.createRoute(
                    categoryName = it.name
                ))
            }
        }
    }
}

fun getCategories(): List<CategoryModel> {
    return listOf(
        CategoryModel("En el ascensor", icon = Icons.Default.Elevator),
        CategoryModel("En la fila del super", icon = Icons.Default.Face),
        CategoryModel("En un velorio", icon = Icons.Default.Dangerous),
        CategoryModel("En la consulta con el proctologo", icon = Icons.Default.Face),
        CategoryModel("En una reunión de consorcio", icon = Icons.Default.Apartment),
        CategoryModel("En una cena con los amigos de tu pareja", icon = Icons.Default.Group),
        CategoryModel("En una reunion de padres", icon = Icons.Default.FamilyRestroom),
        CategoryModel("Cuando vas a pagar el alquiler", icon = Icons.Default.Payments),
        CategoryModel("En el gym", icon = Icons.Default.FitnessCenter),
        CategoryModel("En el colectivo", icon = Icons.Default.DepartureBoard)
    )
}

@Composable
fun ItemCategory(category: CategoryModel, modifier: Modifier, onItemSelected: (CategoryModel) -> Unit) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onItemSelected(category) },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = category.icon,
                contentDescription = "",
                modifier = Modifier.size(64.dp)
            )
            Text(text = category.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

package com.girlify.conversationApp.categories.ui.categoriesScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
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
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel

@Composable
@Preview(showBackground = true)
fun CategoriesScreen(){
    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(10) { it ->
            ItemCategory(category = CategoryModel("CATEGORY $it", icon = Icons.Default.Face)){
                Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun ItemCategory(category: CategoryModel, onItemSelected: (CategoryModel) -> Unit) {
    Card(
        modifier = Modifier
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

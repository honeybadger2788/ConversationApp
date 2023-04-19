package com.girlify.conversationApp.categories.ui.questionScreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import com.girlify.conversationApp.categories.ui.questionScreen.model.CardFace
import com.girlify.conversationApp.categories.ui.questionScreen.model.QuestionModel
import com.girlify.conversationApp.model.Routes
import com.girlify.conversationApp.ui.CardText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    navigationController: NavHostController,
    s: String,
    questionViewModel: QuestionViewModel
) {
    Column(Modifier.fillMaxSize()
    ) {
        TopAppBar(title = { Text(text = s) }, navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                modifier = Modifier
                    .padding(4.dp)
                    .size(32.dp)
                    .clickable {
                        navigationController.navigate(Routes.Categories.route)
                    }
            )
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White
        ))
        LazyRow(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            items(getQuestions()) { question ->
                ItemQuestion(
                    question,
                    Modifier
                        .fillParentMaxSize()
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}

fun getQuestions(): List<QuestionModel> {
    return listOf(
        QuestionModel(
            "¿Qué es lo que más te gusta de viajar en transporte público?"
        ),
        QuestionModel(
            "¿Qué libro o podcast estás leyendo o escuchando actualmente y por qué lo recomendarías?"
        ),
        QuestionModel(
            "¿Cuál es el lugar más hermoso que has visitado en tu vida y por qué?"
        ),
        QuestionModel(
            "¿Qué opinas sobre la idea de \"el amor a primera vista\"?"
        ),
        QuestionModel(
            "¿Cuál ha sido tu mayor reto en la vida y cómo lo superaste?"
        ),
        QuestionModel(
            "¿Qué te gustaría hacer si no tuvieras que trabajar para vivir?"
        )
    )
}

@Composable
fun ItemQuestion(question: QuestionModel,modifier: Modifier) {
    var cardFace by remember {
        mutableStateOf(CardFace.Reverse)
    }

    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing,
        )
    )

    Card(
        modifier = modifier
            .graphicsLayer {
                rotationX = rotation.value
                cameraDistance = 12f * density
            }
            .clickable { cardFace = cardFace.next },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary)
    ) {
        if (rotation.value <= 90f) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ReverseCard("?")
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationX = 180f
                    },
                contentAlignment = Alignment.Center
            ) {
                FrontCard(question.question)
            }
        }

    }
}

@Composable
fun FrontCard(question: String) {
    CardText(text = question)
}

@Composable
fun ReverseCard(text: String) {
    CardText(text = text)
}

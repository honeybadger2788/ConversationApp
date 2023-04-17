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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.girlify.conversationApp.categories.ui.questionScreen.model.QuestionModel
import com.girlify.conversationApp.model.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(navigationController: NavHostController, s: String) {
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
        "¿Qué es lo que más te gusta de viajar en transporte público?",
        "En el colectivo"
    ),
    QuestionModel(
        "¿Qué libro o podcast estás leyendo o escuchando actualmente y por qué lo recomendarías?",
        "En el colectivo"
    ),
    QuestionModel(
        "¿Cuál es el lugar más hermoso que has visitado en tu vida y por qué?",
        "En el colectivo"
    ),
    QuestionModel(
        "¿Qué opinas sobre la idea de \"el amor a primera vista\"?",
        "En el colectivo"
    ),
    QuestionModel(
        "¿Cuál ha sido tu mayor reto en la vida y cómo lo superaste?",
        "En el colectivo"
    ),
    QuestionModel(
        "¿Qué te gustaría hacer si no tuvieras que trabajar para vivir?",
        "En el colectivo"
    )
)
}

enum class CardFace(val angle: Float) {
    Reverse(0f) {
        override val next: CardFace
            get() = Front
    },
    Front(180f) {
        override val next: CardFace
            get() = Reverse
    };

    abstract val next: CardFace
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
                rotationY = rotation.value
                cameraDistance = 12f * density
            }
            .clickable { cardFace = cardFace.next },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(Color(0xFFC1007C))
    ) {
        if (rotation.value <= 90f) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ReverseCard()
            }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f
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
    Text(
        text = question,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp,
        lineHeight = 48.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun ReverseCard() {
    Text(
        text = "?",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp,
        lineHeight = 48.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

package com.girlify.conversationApp.categories.ui.questionScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.girlify.conversationApp.categories.ui.questionScreen.model.QuestionModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun QuestionScreen() {
    Column(Modifier.fillMaxSize()
    ) {
        TopAppBar(title = { Text(text = "CATEGORY") }, navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                modifier = Modifier.padding(4.dp)
            )
        })
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

@Composable
fun ItemQuestion(question: QuestionModel,modifier: Modifier) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    Card(modifier = modifier.clickable { isVisible = !isVisible }, elevation = CardDefaults.cardElevation(8.dp)) {
        AnimatedVisibility(visible = isVisible, enter = fadeIn(), exit = fadeOut()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = question.question,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 42.sp,
                    lineHeight = 48.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

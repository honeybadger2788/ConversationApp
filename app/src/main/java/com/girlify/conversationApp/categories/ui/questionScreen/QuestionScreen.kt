package com.girlify.conversationApp.categories.ui.questionScreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.girlify.conversationApp.R
import com.girlify.conversationApp.categories.ui.UiState
import com.girlify.conversationApp.categories.ui.categoriesScreen.model.CategoryModel
import com.girlify.conversationApp.categories.ui.questionScreen.model.CardFace
import com.girlify.conversationApp.categories.ui.questionScreen.model.toggle
import com.girlify.conversationApp.ui.ErrorComponent
import com.girlify.conversationApp.ui.LoadingComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun QuestionScreen(
    navigationController: NavHostController,
    categoryId: String,
    questionViewModel: QuestionViewModel
) {
    val uiState by produceState<UiState<*>>(initialValue = UiState.Loading) {
        questionViewModel.uiState.collect { value = it }
    }

    LaunchedEffect(Unit) {
        questionViewModel.getQuestions(categoryId)
    }

    when(uiState){
        is UiState.Error ->
            ErrorComponent("Error al cargar las preguntas")

        UiState.Loading ->
            LoadingComponent()

        is UiState.Success -> {
            Column(Modifier.fillMaxSize()
            ) {
                TopBar(
                    (uiState as UiState.Success<CategoryModel>).data.name,
                    navigationController::popBackStack
                )
                QuestionsList((uiState as UiState.Success<CategoryModel>).data.questions)
            }
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(categoryName: String, goBack:() -> Unit) {
    TopAppBar(
        title = { Text(text =  categoryName)},
        navigationIcon = {
            IconButton(onClick = goBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "back")
            }
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
fun QuestionsList(questions: List<String>) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val halfPastItemThreshold = 500
    LazyRow(
        state = listState,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(questions) { question ->
            ItemQuestion(
                question,
                Modifier
                    .fillParentMaxSize()
            )
            if(!listState.isScrollInProgress){
                when {
                    listState.isHalfPastItemLeft(halfPastItemThreshold) ->
                        coroutineScope.scrollBasic(listState, left = true)
                    listState.isHalfPastItemRight(halfPastItemThreshold) ->
                        coroutineScope.scrollBasic(listState)
                    else -> coroutineScope.scrollBasic(listState, left = true)
                }
            }
        }
    }
}

private fun CoroutineScope.scrollBasic(listState: LazyListState, left: Boolean = false) {
    launch {
        val pos: Int =
            if (left) listState.firstVisibleItemIndex else listState.firstVisibleItemIndex + 1
        listState.animateScrollToItem(pos)
    }
}

private fun LazyListState.isHalfPastItemRight(threshold: Int): Boolean {
    return firstVisibleItemScrollOffset > threshold
}

private fun LazyListState.isHalfPastItemLeft(threshold: Int): Boolean {
    return firstVisibleItemScrollOffset <= threshold
}

@Composable
fun ItemQuestion(question: String,modifier: Modifier) {
    var cardFace by remember {
        mutableStateOf(CardFace.Reverse)
    }

    val rotation by animateFloatAsState(
        targetValue = if (cardFace == CardFace.Reverse) 0f else 180f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
    )

    Card(
        modifier = modifier
            .graphicsLayer {
                rotationX = rotation
                cameraDistance = 12f * density
            }
            .clickable { cardFace = cardFace.toggle() },
        border = BorderStroke(8.dp,MaterialTheme.colorScheme.tertiary),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
    ) {
        if (rotation <= 90f) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ReverseCard()
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
                FrontCard(question)
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
        fontSize = 38.sp,
        lineHeight = 44.sp,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun ReverseCard() {
    Image(painterResource(id = R.drawable.question_mark), contentDescription = "?")
}


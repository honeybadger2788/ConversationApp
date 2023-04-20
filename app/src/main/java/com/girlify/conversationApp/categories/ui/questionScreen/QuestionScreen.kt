package com.girlify.conversationApp.categories.ui.questionScreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import com.girlify.conversationApp.categories.ui.questionScreen.model.CardFace
import com.girlify.conversationApp.ui.CardText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun QuestionScreen(
    navigationController: NavHostController,
    s: String,
    questionViewModel: QuestionViewModel
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<QuestionsUiState>(
        initialValue = QuestionsUiState.Loading,
        key1 = lifecycle,
        key2 = questionViewModel
    ){
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            questionViewModel.uiState.collect{ value = it }
        }
    }

    when(uiState){
        is QuestionsUiState.Error -> {}
        QuestionsUiState.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is QuestionsUiState.Success -> {
            Column(Modifier.fillMaxSize()
            ) {
                TopBar((uiState as QuestionsUiState.Success).category.name){
                    navigationController.popBackStack()
                }
                QuestionsList((uiState as QuestionsUiState.Success).category.questions)
            }
        }
    }

    LaunchedEffect(Unit) {
        questionViewModel.getQuestions(s)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(categoryName: String, goBack:() -> Unit) {
    TopAppBar(
        title = { Text(text =  categoryName)},
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                modifier = Modifier
                    .padding(4.dp)
                    .size(32.dp)
                    .clickable {
                        goBack()
                    }
            )
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White
        )
    )
}

@Composable
fun QuestionsList(questions: List<String>) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
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
                if(listState.isHalfPastItemLeft())
                    coroutineScope.scrollBasic(listState, left = true)
                else
                    coroutineScope.scrollBasic(listState)

                if(listState.isHalfPastItemRight())
                    coroutineScope.scrollBasic(listState)
                else
                    coroutineScope.scrollBasic(listState, left = true)
            }
        }
    }
}

private fun CoroutineScope.scrollBasic(listState: LazyListState, left: Boolean = false){
    launch {
        val pos = if(left) listState.firstVisibleItemIndex else listState.firstVisibleItemIndex+1
        listState.animateScrollToItem(pos)
    }
}

@Composable
private fun LazyListState.isHalfPastItemRight(): Boolean {
    return firstVisibleItemScrollOffset > 500
}

@Composable
private fun LazyListState.isHalfPastItemLeft(): Boolean {
    return firstVisibleItemScrollOffset <= 500
}

@Composable
fun ItemQuestion(question: String,modifier: Modifier) {
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
                FrontCard(question)
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

package com.girlify.conversationApp.categories.ui.questionScreen.model

fun CardFace.toggle(): CardFace {
    return if (this == CardFace.Reverse) {
        CardFace.Face
    } else {
        CardFace.Reverse
    }
}

enum class CardFace {
    Reverse, Face
}
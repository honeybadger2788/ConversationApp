package com.girlify.conversationApp.categories.ui.questionScreen.model

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
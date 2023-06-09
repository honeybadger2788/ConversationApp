package com.girlify.conversationApp.model

sealed class Routes(val route: String){
    object Categories:Routes("categories")
    object Questions:Routes("questions/{categoryId}"){
        fun createRoute(categoryId: String) = "questions/$categoryId"
    }
}

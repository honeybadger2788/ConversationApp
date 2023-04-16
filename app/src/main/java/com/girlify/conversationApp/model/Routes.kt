package com.girlify.conversationApp.model

sealed class Routes(val route: String){
    object Categories:Routes("categories")
    object Questions:Routes("questions/{categoryName}"){
        fun createRoute(categoryName: String) = "questions/$categoryName"
    }
}

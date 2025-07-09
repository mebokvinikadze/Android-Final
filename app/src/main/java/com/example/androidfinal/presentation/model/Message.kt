package com.example.androidfinal.presentation.model

import com.example.androidfinal.util.DateUtils
import java.util.UUID

data class Message(
    val id: UUID,
    val text:String,
    val date: String = DateUtils.todayDate()
)
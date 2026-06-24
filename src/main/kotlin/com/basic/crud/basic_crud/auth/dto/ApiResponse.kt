package com.basic.crud.basic_crud.auth.dto

data class ApiResponse(
    val message: String,
)

data class UserResponse(
    val id: Long,
    val email: String
)
package com.basic.crud.basic_crud.auth.dto

import com.basic.crud.basic_crud.auth.entity.Role

data class ApiResponse(
    val message: String,
)

data class LoginResponse(
    val token: String,
)

data class UserResponse(
    val id: Long,
    val email: String,
    val role: Role
)
package com.basic.crud.basic_crud.auth.dto

data class UpdatePasswordRequest(
    val email: String,
    val password: String
)

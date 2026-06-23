package com.basic.crud.basic_crud.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)
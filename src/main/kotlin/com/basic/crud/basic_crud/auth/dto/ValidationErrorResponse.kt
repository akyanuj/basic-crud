package com.basic.crud.basic_crud.auth.dto

data class ValidationErrorResponse(
    val errors: Map<String, String>
)
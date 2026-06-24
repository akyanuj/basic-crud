package com.basic.crud.basic_crud.auth.exception

import com.basic.crud.basic_crud.auth.dto.ApiResponse
import com.basic.crud.basic_crud.auth.dto.ValidationErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiResponse(
                message = ex.message ?: "User not found.",
            )
        )
    }

    @ExceptionHandler(EmailNotFoundException::class)
    fun handleEmailNotFound(ex: EmailNotFoundException): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiResponse(
                message = ex.message ?: "Email not found.",
            )
        )
    }

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleUserNotFound(ex: EmailAlreadyExistsException): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ApiResponse(
                message = ex.message ?: "Email already exists",
            )
        )
    }


    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> {
        println(ex.bindingResult.fieldErrors)
        val errors = ex.bindingResult.fieldErrors
            .associate {
                it.field to (it.defaultMessage ?: "Invalid value")
            }
        return ResponseEntity.badRequest().body(
            ValidationErrorResponse(
                errors = errors
            )
        )
    }
    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(ex: InvalidCredentialsException): ResponseEntity<ApiResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ApiResponse(
                message = ex.message ?: "Invalid credentials",
            )
        )
    }
}
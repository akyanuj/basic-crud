package com.basic.crud.basic_crud.auth.controller

import com.basic.crud.basic_crud.auth.dto.ApiResponse
import com.basic.crud.basic_crud.auth.dto.LoginRequest
import com.basic.crud.basic_crud.auth.dto.RegisterRequest
import com.basic.crud.basic_crud.auth.dto.UpdatePasswordRequest
import com.basic.crud.basic_crud.auth.dto.UserResponse
import com.basic.crud.basic_crud.auth.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


//"/auth/register"
@RestController
@RequestMapping("/auth")
class AuthController(
    val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
        @Valid
        @RequestBody request: RegisterRequest
    ): ResponseEntity<UserResponse> {
        println(request)
        val result = authService.register(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            result
        )
    }

    @GetMapping("/user")
    fun getUser(
        @RequestParam email: String,
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(authService.getUser(email))
    }

    @PutMapping("/updatepassword")
    fun updatePassword(
        @RequestBody updatePasswordRequest: UpdatePasswordRequest
    ): ResponseEntity<ApiResponse> {
        println(updatePasswordRequest)
        val result =  authService.updatePassword(updatePasswordRequest)
        return ResponseEntity.ok(
            ApiResponse(message = result)
        )
    }

    @DeleteMapping("/user")
    fun deleteUser(
        @RequestParam email: String,
    ): ResponseEntity<ApiResponse> {
        println(email)
        val result = authService.deleteUser(email)
        return ResponseEntity.ok(
            ApiResponse(message = result)
        )
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest: LoginRequest) : ResponseEntity<ApiResponse> {
        println(loginRequest)
        val result =authService.login(loginRequest)
        return ResponseEntity.ok(
            ApiResponse(message = result)
        )
    }
}

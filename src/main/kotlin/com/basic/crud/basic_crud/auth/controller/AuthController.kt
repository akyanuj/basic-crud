package com.basic.crud.basic_crud.auth.controller

import com.basic.crud.basic_crud.auth.dto.RegisterRequest
import com.basic.crud.basic_crud.auth.dto.UpdatePasswordRequest
import com.basic.crud.basic_crud.auth.entity.UserEntity
import com.basic.crud.basic_crud.auth.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


//"/auth/register"
@RestController
@RequestMapping("/auth")
class AuthController(
    val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest
    ): String {
        println(request)
        return authService.register(request)
    }

    @GetMapping("/user")
    fun  getUser(
        @RequestParam email : String,
    ): UserEntity? {
        return authService.getUser(email)
    }

    @PutMapping("/updatepassword")
    fun updatePassword(
        @RequestBody updatePasswordRequest: UpdatePasswordRequest
    ): String {
        println(updatePasswordRequest)
        return authService.updatePassword(updatePasswordRequest)
    }
}

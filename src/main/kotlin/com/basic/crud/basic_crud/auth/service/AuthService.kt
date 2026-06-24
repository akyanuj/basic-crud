package com.basic.crud.basic_crud.auth.service

import com.basic.crud.basic_crud.auth.dto.LoginRequest
import com.basic.crud.basic_crud.auth.dto.RegisterRequest
import com.basic.crud.basic_crud.auth.dto.UpdatePasswordRequest
import com.basic.crud.basic_crud.auth.dto.UserResponse
import com.basic.crud.basic_crud.auth.entity.UserEntity
import com.basic.crud.basic_crud.auth.exception.EmailAlreadyExistsException
import com.basic.crud.basic_crud.auth.exception.EmailNotFoundException
import com.basic.crud.basic_crud.auth.exception.InvalidCredentialsException
import com.basic.crud.basic_crud.auth.exception.UserNotFoundException
import com.basic.crud.basic_crud.auth.repository.UserRepository
import com.basic.crud.basic_crud.auth.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

// handles service is now responsible for the business operation.
@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
) {
    fun register(
        request: RegisterRequest,
    ): UserResponse {
        println("AuthService called")
        if (userRepository.existsByEmail(request.email)) {
            throw EmailAlreadyExistsException("Email already exists")
        }
        val encodedPassword = passwordEncoder.encode(request.password)!!
        println(encodedPassword)

        val user = UserEntity(
            email = request.email,
            password = encodedPassword
        )
        val savedUser = userRepository.save(user)
        return UserResponse(savedUser.id!!, savedUser.email, savedUser.role)
    }

    fun getUser(email: String): UserResponse {
        val savedUser = userRepository.findByEmail(email) ?: throw UserNotFoundException("User not found")
        return UserResponse(savedUser.id!!, savedUser.email, savedUser.role)
    }

    fun updatePassword(updatePasswordRequest: UpdatePasswordRequest): String {
        val savedUser = userRepository.findByEmail(
            updatePasswordRequest.email
        ) ?: throw EmailNotFoundException("Email doesn't exist")
        val updatedUser = UserEntity(
            id = savedUser.id,
            email = savedUser.email,
            password = updatePasswordRequest.password
        )
        userRepository.save(updatedUser)
        return "Password updated"
    }

    fun deleteUser(email: String): String {
        val savedUser = userRepository.findByEmail(email) ?: throw UserNotFoundException("Email doesn't exist")
        userRepository.delete(savedUser)
        return "User deleted Successfully"
    }

    fun login(loginRequest: LoginRequest): String {
        val savedUser =
            userRepository.findByEmail(loginRequest.email) ?: throw UserNotFoundException("Email doesn't exist")
        val isLoggedIn = passwordEncoder.matches(loginRequest.password, savedUser.password)
        if (!isLoggedIn) {
            throw InvalidCredentialsException("Invalid credentials")
        }
        val token = jwtService.generateToken(savedUser)
        println("Extracted User Id = ${jwtService.extractUserId(token)}")
        println("Token Valid = ${jwtService.isTokenValid(token)}")
        return token
    }

    fun getCurrentUser(
        userId: Long
    ): UserResponse{
        val user = userRepository.findById(userId)
            .orElseThrow {
                UserNotFoundException("User not found")
            }

        return UserResponse(
            id = user.id!!,
            email = user.email,
            role = user.role
        )
    }
}
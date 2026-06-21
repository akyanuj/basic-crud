package com.basic.crud.basic_crud.auth.service

import com.basic.crud.basic_crud.auth.dto.RegisterRequest
import com.basic.crud.basic_crud.auth.dto.UpdatePasswordRequest
import com.basic.crud.basic_crud.auth.entity.UserEntity
import com.basic.crud.basic_crud.auth.repository.UserRepository
import org.springframework.stereotype.Service

// handles service is now responsible for the business operation.
@Service
class AuthService(
    private val userRepository: UserRepository,
) {
    fun register(
        request: RegisterRequest,
    ): String {
        println("AuthService called")
        //validation
        if (userRepository.existsByEmail(request.email)) {
            return "Email already exists"
        }

        val user = UserEntity(
            email = request.email,
            password = request.password
        )
        val savedUser = userRepository.save(user)
        return savedUser.email
    }

    fun getUser(email: String): UserEntity? {
        val savedUser = userRepository.findByEmail(email)
        return savedUser
    }

    fun updatePassword(updatePasswordRequest: UpdatePasswordRequest): String {
        val savedUser = userRepository.findByEmail(
            updatePasswordRequest.email
        ) ?: return "Email doesn't exist"
        val updatedUser = UserEntity(
            id = savedUser.id,
            email = savedUser.email,
            password = updatePasswordRequest.password
        )
        userRepository.save(updatedUser)
        return "Password updated"
    }
}
package com.basic.crud.basic_crud.auth.security

import com.basic.crud.basic_crud.auth.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(
        username: String): UserDetails {
         val user = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException(
                "User not found"
            )
        return CustomUserDetails(user)
    }

    fun loadUserById(
        id: Long
    ): UserDetails {
        val user = userRepository.findById(id)
            .orElseThrow {
                UsernameNotFoundException(
                    "User not found"
                )
            }
        return CustomUserDetails(user)
    }
}
package com.basic.crud.basic_crud.auth.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authHeader = request.getHeader("Authorization")
        println("URI = ${request.requestURI}")
        println("Auth Header = $authHeader")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        val token = authHeader.substring(7)
        println("Token = $token")
        println("Token Valid = ${jwtService.isTokenValid(token)}")

        if (!jwtService.isTokenValid(token)) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }
        val userId = jwtService.extractUserId(token)
        println("User Id = $userId")
        val userDetails = customUserDetailsService.loadUserById(userId.toLong())
        println("User Details Loaded = ${userDetails.username}")
        val authentication =
            UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities
            )
        if (SecurityContextHolder
                .getContext()
                .authentication == null
        ) {
            SecurityContextHolder
                .getContext()
                .authentication = authentication
        }
        println("Authentication Set Successfully")


        filterChain.doFilter(
            request,
            response
        )
    }
}
/*
Request
↓
Authorization Header
↓
Extract JWT
↓
Validate JWT
↓
Extract UserId
↓
Load UserDetails
↓
Create Authentication
↓
SecurityContextHolder
↓
Continue Filter Chain
↓
Controller*/

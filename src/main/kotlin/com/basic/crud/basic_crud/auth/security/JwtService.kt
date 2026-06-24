package com.basic.crud.basic_crud.auth.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import com.basic.crud.basic_crud.auth.entity.UserEntity
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.SecretKey


//JwtService
//├── generateToken()
//├── extractUserId()
//└── validateToken()

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String
) {
    fun generateToken(user: UserEntity): String {
        val now = Date()
        val expiry = Date(now.time + 1000*60*60) // 1 hr

        val key = getSigningKey()
        return Jwts.builder()
            .subject(user.id.toString())
            .claim("role", user.role.name)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(key)
            .compact()
    }

    fun getSigningKey(): SecretKey {
        val key = Keys.hmacShaKeyFor(
            secret.toByteArray(StandardCharsets.UTF_8)
        )
        return key
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun extractUserId(
        token: String
    ): String {

        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }
}
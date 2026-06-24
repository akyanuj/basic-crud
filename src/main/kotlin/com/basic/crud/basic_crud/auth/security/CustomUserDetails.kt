package com.basic.crud.basic_crud.auth.security

import com.basic.crud.basic_crud.auth.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
     val user: UserEntity
): UserDetails {
    override fun getAuthorities(): Collection<out GrantedAuthority> {
        return listOf(
            SimpleGrantedAuthority(
                "ROLE_${user.role.name}"
            )
        )
    }

    override fun getPassword(): String? {
        return user.password
    }

    override fun getUsername(): String {
       return user.email
    }

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}
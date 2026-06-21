package com.basic.crud.basic_crud.auth.entity

import jakarta.persistence.*


@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val email: String = "",
    val password: String = "",
)